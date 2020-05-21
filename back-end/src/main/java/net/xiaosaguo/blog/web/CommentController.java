package net.xiaosaguo.blog.web;

import net.xiaosaguo.blog.po.Comment;
import net.xiaosaguo.blog.service.BlogService;
import net.xiaosaguo.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * description: 评论 Controller
 *
 * @author xiaosaguo
 * @date 2020/05/21
 */
@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String defaultAvatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.get(blogId));
        comment.setAvatar(defaultAvatar);
        commentService.save(comment);
        return "redirect:/comments/" + blogId;
    }

}
