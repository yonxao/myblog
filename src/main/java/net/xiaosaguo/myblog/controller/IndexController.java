package net.xiaosaguo.myblog.controller;

import net.xiaosaguo.myblog.po.Tag;
import net.xiaosaguo.myblog.po.Type;
import net.xiaosaguo.myblog.service.BlogService;
import net.xiaosaguo.myblog.service.TagService;
import net.xiaosaguo.myblog.service.TypeService;
import net.xiaosaguo.myblog.vo.BlogQuery;
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
 * description: 前台 Controller
 *
 * @author xiaosaguo
 * @date 2020/04/14
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
    public static final String TYPES_VIEW = "types";
    public static final String TAGS_VIEW = "tags";
    public static final String ARCHIVES_VIEW = "archives";
    public static final String ABOUT_VIEW = "about";
    public static final String SEARCH_VIEW = "search";
    public static final String BLOG_VIEW = "blog";
    public static final String FRAGMENT_FOOTER_BLOG_LIST_VIEW = "_fragments::footerBlogList";

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
        // 在分类页面，查出所有分类，并根据其含有的博客数量排序
        List<Type> typeList = typeService.listTop(999);
        // -1 为默认值，如果直接点击导航时请求此接口，此时没有分类 id 值，需要获取实际查询出的第一个分类的 id
        if (id == -1) {
            id = typeList.get(0).getId();
        }
        model.addAttribute("types", typeList);
        model.addAttribute("activeTypeId", id);
        BlogQuery blogQuery = BlogQuery.builder().typeId(id).build();
        model.addAttribute("page", blogService.list(pageable, blogQuery));
        return TYPES_VIEW;
    }

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                       @PathVariable Long id,
                       Model model) {
        // 在标签页面，查出所有标签，并根据其含有的博客数量排序
        List<Tag> tagList = tagService.listTop(999);
        // -1 为默认值，如果直接点击导航时请求此接口，此时没有分类 id 值，需要获取实际查询出的第一个分类的 id
        if (id == -1) {
            id = tagList.get(0).getId();
        }
        model.addAttribute("tags", tagList);
        model.addAttribute("activeTagId", id);
        model.addAttribute("page", blogService.list(id, pageable));
        return TAGS_VIEW;
    }

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archivesMap", blogService.archivesBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return ARCHIVES_VIEW;
    }

    @GetMapping("/about")
    public String about() {
        return ABOUT_VIEW;
    }

    @GetMapping("/footer/blogs")
    public String footerBlogList(Model model) {
        // 查询出最新推荐的前 3 个博客
        model.addAttribute("blogs", blogService.listRecommendTop(3));
        return FRAGMENT_FOOTER_BLOG_LIST_VIEW;
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
