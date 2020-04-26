package net.xiaosaguo.blog.dao;

import net.xiaosaguo.blog.po.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description: 分类Repository
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
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
     * @version 1 xiaosaguo 创建
     */
    Type findByName(String name);
}
