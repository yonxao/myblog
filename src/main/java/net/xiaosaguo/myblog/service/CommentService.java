package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.po.Comment;

import java.util.List;

/**
 * description: 评论 service
 *
 * @author xiaosaguo
 * @date 2020/05/21
 */
public interface CommentService {

    /**
     * description: 根据博客 id 查询该博客的所有评论
     *
     * @param blogId 博客 id
     * @return 该博客的所有评论
     * @author xiaosaguo
     * @date 2020/05/21 07:08
     */
    List<Comment> listByBlogId(Long blogId);

    /**
     * description: 保存
     *
     * @param comment 评论
     * @return 保存后的评论
     * @author xiaosaguo
     * @date 2020/05/21 07:09
     */
    Comment save(Comment comment);
}
