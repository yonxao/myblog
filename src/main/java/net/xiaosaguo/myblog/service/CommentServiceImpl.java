package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.dao.CommentRepository;
import net.xiaosaguo.myblog.exception.NotFoundException;
import net.xiaosaguo.myblog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 评论 ServiceImpl
 *
 * @author xiaosaguo
 * @date 2020/05/21
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listByBlogId(Long blogId) {
        // 该博客的顶层评论
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, Sort.by("createTime"));
        return transCommentList(commentList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment save(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(
                    commentRepository.findById(parentCommentId).orElseThrow(() -> new NotFoundException("父评论不存在")));
        } else {
            comment.setParentComment(null);
        }
        return commentRepository.save(comment);
    }

    /**
     * 深拷贝一个 commentList
     *
     * @param commentList 评论
     */
    private List<Comment> transCommentList(List<Comment> commentList) {
        List<Comment> newList = new ArrayList<>();
        for (Comment comment : commentList) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            newList.add(c);
        }

        for (Comment comment : newList) {
            // 如果顶层评论有回复，将回复放在临时集合中
            if (!comment.getReplyComments().isEmpty()) {
                // 要遍历出的顶层评论所有子评论的集合
                List<Comment> tempReplyList = new ArrayList<>();
                getTopChild(comment, tempReplyList);
                comment.setReplyComments(tempReplyList);
            }
        }
        return newList;
    }

    /**
     * 递归迭代将所有子回复聚集到顶层评论下
     *
     * @param comment       被迭代的对象
     * @param tempReplyList 要遍历出的顶层评论所有子评论的集合
     */
    private void getTopChild(Comment comment, List<Comment> tempReplyList) {
        // 如果顶层评论有回复，将回复放在临时集合中
        if (!comment.getReplyComments().isEmpty()) {
            tempReplyList.addAll(comment.getReplyComments());
            for (Comment replyComment : comment.getReplyComments()) {
                getTopChild(replyComment, tempReplyList);
            }
        }
    }
}
