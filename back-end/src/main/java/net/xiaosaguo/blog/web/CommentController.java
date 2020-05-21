package net.xiaosaguo.blog.web;

import net.xiaosaguo.blog.po.Blog;
import net.xiaosaguo.blog.po.Comment;
import net.xiaosaguo.blog.po.User;
import net.xiaosaguo.blog.service.BlogService;
import net.xiaosaguo.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
    public String post(@Valid Comment comment, BindingResult bindingResult, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "blog :: commentList";
        }

        Blog blog = blogService.get(comment.getBlog().getId());
        comment.setBlog(blog);
        User user = (User) session.getAttribute("user");
        // 如果当前有用户登录，并且评论中的昵称和邮箱与当前登录用户的一致，则头像使用当前登录用户的
        if (user != null
                && user.getNickname().equals(comment.getNickname()) && user.getEmail().equals(comment.getEmail())) {
            comment.setAvatar(user.getAvatar());
            // 如果当前登录的用户和博主是同一人，则标记为评论为博主的
            if (user.getId().equals(blog.getUser().getId())) {
                comment.setOwner(true);
            }
        } else {
            comment.setAvatar(defaultAvatar);
        }
        commentService.save(comment);
        return "redirect:/comments/" + blog.getId();
    }

}
