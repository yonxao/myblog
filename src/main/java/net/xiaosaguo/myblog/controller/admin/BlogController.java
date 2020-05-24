package net.xiaosaguo.myblog.controller.admin;

import net.xiaosaguo.myblog.pojo.entity.Blog;
import net.xiaosaguo.myblog.pojo.entity.User;
import net.xiaosaguo.myblog.pojo.query.BlogListSearchQuery;
import net.xiaosaguo.myblog.service.BlogService;
import net.xiaosaguo.myblog.service.TagService;
import net.xiaosaguo.myblog.service.TypeService;
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

/**
 * description: 博客 Controller
 *
 * @author xiaosaguo
 * @date 2020/04/26
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogController {

    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;

    private static final String LIST_VIEW = "admin/blog";
    private static final String REDIRECT_LIST_VIEW = "redirect:/admin/blog";
    private static final String INPUT_VIEW = "admin/blog-input";
    private static final String FRAGMENT_BLOG_BLOG_LIST_VIEW = "/admin/blog::blogList";

    @GetMapping
    public String list(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       BlogListSearchQuery blogListSearchQuery,
                       Model model) {
        model.addAttribute("page", blogService.list(pageable, blogListSearchQuery));
        model.addAttribute("typeList", typeService.list());
        return LIST_VIEW;
    }

    @PostMapping("/search")
    public String search(@PageableDefault(sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogListSearchQuery blogListSearchQuery,
                         Model model) {
        model.addAttribute("page", blogService.list(pageable, blogListSearchQuery));
        return FRAGMENT_BLOG_BLOG_LIST_VIEW;
    }

    @GetMapping("/save")
    public String saveView(Model model) {
        model.addAttribute("blog", new Blog());
        initTypesAndTags(model);
        return INPUT_VIEW;
    }

    @PostMapping
    public String save(@Valid Blog blog, BindingResult bindingResult,
                       HttpSession session,
                       Model model,
                       RedirectAttributes attributes) {
        // 如果可以自定义标签，得到的参数示例： 3,study
        // 参数校验
        if (bindingResult.hasErrors()) {
            model.addAttribute("blog", blog);
            initTypesAndTags(model);
            return INPUT_VIEW;
        }
        // 处理博客创建人
        blog.setUser((User) session.getAttribute("user"));
        // 处理博客分类
        blog.setType(typeService.get(blog.getType().getId()));
        // 处理博客标签
        blog.setTags(tagService.listByIds(blog.getTagIds()));
        // 判断新增和修改处理处理方法
        if (ObjectUtils.isEmpty(blog.getId())) {
            blogService.save(blog);
        } else {
            blogService.update(blog.getId(), blog);
        }
        attributes.addFlashAttribute("message", "操作成功");
        return REDIRECT_LIST_VIEW;
    }

    @GetMapping("/update/{id}")
    public String updateVies(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.get(id));
        initTypesAndTags(model);
        return INPUT_VIEW;
    }


    @GetMapping("/delete/{id}")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return REDIRECT_LIST_VIEW;
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
}
