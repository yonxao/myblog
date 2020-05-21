package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.CommentRepository;
import net.xiaosaguo.blog.exception.NotFoundException;
import net.xiaosaguo.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        return commentRepository.findByBlogId(blogId, sort);
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
}
