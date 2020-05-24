package net.xiaosaguo.myblog.controller;

import net.xiaosaguo.myblog.pojo.entity.Blog;
import net.xiaosaguo.myblog.pojo.entity.Comment;
import net.xiaosaguo.myblog.pojo.entity.User;
import net.xiaosaguo.myblog.service.BlogService;
import net.xiaosaguo.myblog.service.CommentService;
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

    public static final String FRAGMENT_BLOG_COMMENT_LIST_VIEW = "blog::commentList";

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listByBlogId(blogId));
        return FRAGMENT_BLOG_COMMENT_LIST_VIEW;
    }

    @PostMapping("/comments")
    public String post(@Valid Comment comment, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return FRAGMENT_BLOG_COMMENT_LIST_VIEW;
        }
        // 设置 blog 关联
        Blog blog = blogService.get(comment.getBlog().getId());
        comment.setBlog(blog);
        // 处理管理员头像和博主评论
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
