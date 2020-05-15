package net.xiaosaguo.blog.web;

import net.xiaosaguo.blog.service.BlogService;
import net.xiaosaguo.blog.service.TagService;
import net.xiaosaguo.blog.service.TypeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

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

    @GetMapping("/")
    public String index(@PageableDefault(sort = "updateTime", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.list(pageable));
        model.addAttribute("types", typeService.listTop(6));
        model.addAttribute("tags", tagService.listTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendTop(10));
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
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
