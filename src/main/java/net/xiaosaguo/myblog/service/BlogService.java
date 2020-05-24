package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.pojo.entity.Blog;
import net.xiaosaguo.myblog.pojo.query.BlogListSearchQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;


/**
 * description: 博客Service
 *
 * @author xiaosaguo
 * @date 2020/05/02
 */
public interface BlogService {
    /**
     * description: 新增 {@code Blog}
     *
     * @param blog 要保存的 {@code Blog}
     * @return 保存成功后返回的的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/04 02:35
     */
    Blog save(Blog blog);

    /**
     * description: 根据ID查询
     *
     * @param id {@code Blog} 的ID
     * @return 根据 {@code id} 查询出的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/02 09:41
     */
    Blog get(long id);

    /**
     * description: 根据ID查询
     *
     * @param id {@code Blog} 的ID
     * @return 根据 {@code id} 查询出的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/02 09:41
     */
    Blog getAndConvert(long id);

    /**
     * description: 根据条件分页查询
     *
     * @param pageable  分页参数
     * @param blogListSearchQuery {@code BlogQuery} 的查询条件
     * @return {@code Page<Blog>} 根据条件查询出的包装了分页信息的结果集
     * @author xiaosaguo
     * @date 2020/05/02 10:18
     */
    Page<Blog> list(Pageable pageable, BlogListSearchQuery blogListSearchQuery);

    /**
     * description: 根据标签分页查询所有博客
     *
     * @param pageable 分页参数
     * @param tagId    {@code tagId} 标签的主键
     * @return {@code Page<Blog>} 根据条件查询出的包装了分页信息的结果集
     * @author xiaosaguo
     * @date 2020/05/02 10:18
     */
    Page<Blog> list(Long tagId, Pageable pageable);

    /**
     * description: 根据条件分页查询
     *
     * @param pageable 分页参数
     * @return {@code Page<Blog>} 根据条件查询出的包装了分页信息的结果集
     * @author xiaosaguo
     * @date 2020/05/02 10:18
     */
    Page<Blog> list(Pageable pageable);

    /**
     * description: 筛选最新推荐的前几个博客
     *
     * @param size 前几名，页面大小
     * @return 根据条件筛选的前几名的博客
     * @author xiaosaguo
     * @date 2020/05/15 23:54
     */
    List<Blog> listRecommendTop(int size);

    /**
     * description: 筛选符合条件的前几个博客
     *
     * @param size 条件参数
     * @param sort 排序条件
     * @return 根据条件筛选的前几个博客
     * @author xiaosaguo
     * @date 2020/05/16 01:01
     */
    List<Blog> listTop(int size, Sort sort);

    /**
     * description: 根据关键字对博客标题和内容进行匹配
     *
     * @param keyword  模糊查询关键字
     * @param pageable 分页参数
     * @return 根据关键字进行模糊查询匹配到的结果
     * @author xiaosaguo
     * @date 2020/05/19 23:55
     */
    Page<Blog> likeTitleOrContent(String keyword, Pageable pageable);

    /**
     * description: 博客归档
     *
     * @return 归档后的博客，Map key 为年份，value 为该年份博客集合
     * @author xiaosaguo
     * @date 2020/05/22 11:15
     */
    Map<String, List<Blog>> archivesBlog();

    /**
     * description: 统计有多少篇博客
     *
     * @return 博客总数
     * @author xiaosaguo
     * @date 2020/05/22 12:01
     */
    Long countBlog();

    /**
     * description: 根据ID更新
     *
     * @param id   {@code Blog} 的ID
     * @param blog 要更新的 {@code Blog}
     * @return 更新成功后返回的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/04 02:58
     */
    Blog update(long id, Blog blog);

    /**
     * description: 根据ID删除
     *
     * @param id {@code Blog} 的ID
     * @author xiaosaguo
     * @date 2020/05/04 03:02
     */
    void delete(long id);
}
