package net.xiaosaguo.myblog.dao;

import net.xiaosaguo.myblog.pojo.entity.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description: 分类Repository
 *
 * @author xiaosaguo
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    /**
     * description: 根据分类名称查找分类
     *
     * @param name 分类名称
     * @return net.xiaosaguo.blog.po.Type
     * @author xiaosaguo
     * @date 2020-04-26 08:33
     */
    Type findByName(String name);

    /**
     * description: 根据分类下的博客数量排名，返回前几名
     *
     * @param pageable 分页封装参数
     * @return 前几名的 {@code Type}
     * @author xiaosaguo
     * @date 2020/05/15 23:24
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
