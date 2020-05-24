package net.xiaosaguo.myblog.dao;

import net.xiaosaguo.myblog.pojo.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * description: 根据关键字对博客标题和内容进行匹配
     *
     * @param keyword  模糊查询关键字
     * @param pageable 分页参数
     * @return 根据关键字进行模糊查询匹配到的结果
     * @author xiaosaguo
     * @date 2020/05/19 23:55
     */
    @Query("select blog from Blog blog where upper(blog.title)  like upper(?1)  or upper(blog.content)  like upper(?1)")
    Page<Blog> likeTitleOrContent(String keyword, Pageable pageable);

    /**
     * description: 给博客浏览次数 +1
     *
     * @param id 博客id
     * @return int 受影响的行数
     * @author xiaosaguo
     * @date 2020/05/21 21:33
     */
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    /**
     * description: 根据博客的更新时间拿到所有的年份
     *
     * @return 发布博客的所有年份
     * @author xiaosaguo
     * @date 2020/05/22 11:22
     */
    @Query(value = "SELECT date_format(b.update_time, '%Y') AS year FROM t_blog b GROUP BY year ORDER BY year DESC",
            nativeQuery = true)
    List<String> findYearList();

    /**
     * description: 根据年份查询所有博客
     *
     * @param year 年份
     * @return 根据年份查询到的博客集合
     * @author xiaosaguo
     * @date 2020/05/22 11:54
     */
    @Query(value = "SELECT * FROM t_blog b WHERE date_format(b.update_time, '%Y') = ?", nativeQuery = true)
    List<Blog> findByYear(String year);
}
