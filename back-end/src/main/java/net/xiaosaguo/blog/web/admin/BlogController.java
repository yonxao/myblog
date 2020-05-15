package net.xiaosaguo.blog.web.admin;

import net.xiaosaguo.blog.po.Blog;
import net.xiaosaguo.blog.po.Tag;
import net.xiaosaguo.blog.po.User;
import net.xiaosaguo.blog.service.BlogService;
import net.xiaosaguo.blog.service.TagService;
import net.xiaosaguo.blog.service.TypeService;
import net.xiaosaguo.blog.vo.BlogQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * description: 博客操作控制器
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blog";
    private static final String REDIRECT_LIST = "redirect:/admin/blog";

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    @GetMapping
    public String list(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       BlogQuery blogQuery,
                       Model model) {
        model.addAttribute("page", blogService.list(pageable, blogQuery));
        model.addAttribute("typeList", typeService.list());
        return LIST;
    }

    @PostMapping("/search")
    public String search(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blogQuery,
                         Model model) {
        model.addAttribute("page", blogService.list(pageable, blogQuery));
        return "/admin/blog :: blogList";
    }

    @GetMapping("/save")
    public String saveView(Model model) {
        model.addAttribute("blog", new Blog());
        initTypesAndTags(model);
        return INPUT;
    }

    /**
     * description: 给页面初始化一些固定信息
     *
     * @param model 数据传输载体
     * @author xiaosaguo
     * @date 2020/05/14 21:17
     */
    private void initTypesAndTags(Model model) {
        // 初始化分类和标签列表
        model.addAttribute("typeList", typeService.list());
        model.addAttribute("tagList", tagService.list());
    }

    @PostMapping
    public String save(@Valid Blog blog, BindingResult bindingResult,
                       HttpSession session,
                       Model model,
                       RedirectAttributes attributes) {
        // 参数校验 如果可以自定义标签，得到的参数： 3,xuexi
        if (bindingResult.hasErrors()) {
            model.addAttribute("blog", blog);
            initTypesAndTags(model);
            return INPUT;
        }
        // 处理博客创建人
        blog.setUser((User) session.getAttribute("user"));
        // 处理博客分类
        blog.setType(typeService.get(blog.getType().getId()));
        // 处理博客标签
        String ids = blog.getTagIds();
        List<Tag> tags = tagService.listByIds(ids);
        blog.setTags(tagService.listByIds(blog.getTagIds()));
        // 判断新增和修改处理处理方法
        if (ObjectUtils.isEmpty(blog.getId())) {
            blogService.save(blog);
        } else {
            blogService.update(blog.getId(), blog);
        }
        attributes.addFlashAttribute("message", "操作成功");
        return REDIRECT_LIST;
    }

    @GetMapping("/update/{id}")
    public String updateVies(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.get(id));
        initTypesAndTags(model);
        return INPUT;
    }


    @GetMapping("/delete/{id}")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST;
    }


}
