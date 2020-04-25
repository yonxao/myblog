package net.xiaosaguo.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * description: 首页控制器
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        int i = 9 / 0;
//        String blog = null;
//        if (blog == null) {
//            throw new NotFoundException("博客不存在");
//        }
        System.out.println("----------index----------");
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }
}
