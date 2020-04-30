package net.xiaosaguo.blog.dao;

import net.xiaosaguo.blog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * description: 标签资源库
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * description: 根据标签名称查询标签
     *
     * @param name 标签名称
     * @return net.xiaosaguo.blog.po.Tag
     * @author xiaosaguo
     * @date 2020-04-30 20:04
     */
    Tag findByName(String name);
}
