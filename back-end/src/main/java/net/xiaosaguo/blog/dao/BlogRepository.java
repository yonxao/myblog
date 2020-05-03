package net.xiaosaguo.blog.dao;

import net.xiaosaguo.blog.po.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description: 博客DAO
 *
 * @author xiaosaguo
 * @date 2020/05/01
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
