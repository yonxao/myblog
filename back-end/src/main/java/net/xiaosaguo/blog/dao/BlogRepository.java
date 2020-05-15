package net.xiaosaguo.blog.dao;

import net.xiaosaguo.blog.po.Blog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: 博客DAO
 *
 * @author xiaosaguo
 * @date 2020/05/01
 */
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * description: 筛选符合条件的前几个博客
     *
     * @param pageable 条件参数
     * @return 根据条件筛选的前几个博客
     * @author xiaosaguo
     * @date 2020/05/16 01:01
     */
    @Query("select t from Blog t")
    List<Blog> findTop(Pageable pageable);

    /**
     * description: 筛选最新推荐的前几个博客
     *
     * @param pageable 条件参数
     * @return 根据条件筛选的前几个博客
     * @author xiaosaguo
     * @date 2020/05/16 01:01
     */
    @Query("select t from Blog t where t.recommend = true")
    List<Blog> findRecommendTop(Pageable pageable);
}
