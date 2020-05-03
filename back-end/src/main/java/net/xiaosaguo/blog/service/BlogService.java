package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * description: 博客Service
 *
 * @author xiaosaguo
 * @date 2020/05/02
 */
public interface BlogService {

    /**
     * description: 根据ID查询
     *
     * @param id {@code Blog} 的ID
     * @return 根据 {@code id} 查询出的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/02 09:41
     */
    Blog get(Long id);

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
     * description: 新增 {@code Blog}
     *
     * @param blog 要保存的 {@code Blog}
     * @return 保存成功后返回的的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/04 02:35
     */
    Blog save(Blog blog);

    /**
     * description: 根据ID更新
     *
     * @param id   {@code Blog} 的ID
     * @param blog 要更新的 {@code Blog}
     * @return 更新成功后返回的 {@code Blog}
     * @author xiaosaguo
     * @date 2020/05/04 02:58
     */
    Blog update(Long id, Blog blog);

    /**
     * description: 根据ID删除
     *
     * @param id {@code Blog} 的ID
     * @author xiaosaguo
     * @date 2020/05/04 03:02
     */
    void delete(Long id);
}
