package net.xiaosaguo.blog.dao;

import net.xiaosaguo.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: 评论 Repository
 *
 * @author xiaosaguo
 * @date 2020/05/21
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * description: 根据博客 id 查询该博客的所有评论
     *
     * @param blogId 博客 id
     * @param sort   排序参数
     * @return 该博客的所有评论
     * @author xiaosaguo
     * @date 2020/05/21 07:08
     */
    List<Comment> findByBlogId(Long blogId, Sort sort);

}
