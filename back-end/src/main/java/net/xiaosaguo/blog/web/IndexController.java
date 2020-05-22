package net.xiaosaguo.blog.web;

import net.xiaosaguo.blog.po.Tag;
import net.xiaosaguo.blog.po.Type;
import net.xiaosaguo.blog.service.BlogService;
import net.xiaosaguo.blog.service.TagService;
import net.xiaosaguo.blog.service.TypeService;
import net.xiaosaguo.blog.vo.BlogQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * description: 首页控制器
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Controller
public class IndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    public static final String INDEX_VIEW = "index";
    public static final String SEARCH_VIEW = "search";
    public static final String BLOG_VIEW = "blog";
    public static final String TAGS_VIEW = "tags";
    public static final String TYPES_VIEW = "types";
    public static final String ARCHIVES_VIEW = "archives";
    public static final String ABOUT_VIEW = "about";

    @GetMapping("/")
    public String index(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.list(pageable));
        model.addAttribute("types", typeService.listTop(6));
        model.addAttribute("tags", tagService.listTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendTop(10));
        return INDEX_VIEW;
    }

    @GetMapping("/search")
    public String search(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String keyword,
                         Model model) {
        model.addAttribute("page", blogService.likeTitleOrContent("%" + keyword + "%", pageable));
        model.addAttribute("keyword", keyword);
        return SEARCH_VIEW;
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return BLOG_VIEW;
    }

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id,
                        Model model) {
        List<Type> typeList = typeService.listTop(999);
        if (id == -1) {
            id = typeList.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", typeList);
        model.addAttribute("page", blogService.list(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return TYPES_VIEW;
    }

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id,
                       Model model) {
        List<Tag> tagList = tagService.listTop(999);
        if (id == -1) {
            id = tagList.get(0).getId();
        }
        model.addAttribute("tags", tagList);
        model.addAttribute("page", blogService.list(id, pageable));
        model.addAttribute("activeTagId", id);
        return TAGS_VIEW;
    }

    @GetMapping("/archives")
    public String archives() {
        return ARCHIVES_VIEW;
    }

    @GetMapping("/about")
    public String about() {
        return ABOUT_VIEW;
    }

    @GetMapping("/test")
    public Object test() {
        /// int i = 9 / 0;
        /// String blog = null;
        /// if (blog == null) {
        ///     throw new NotFoundException("博客不存在");
        /// }
        /// System.out.println("----------index----------");
        return null;
    }
}
