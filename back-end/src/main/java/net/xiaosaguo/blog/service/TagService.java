package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * description: 标签的相关接口
 *
 * @author xiaosaguo
 * @date 2020-04-28 05:29
 */
public interface TagService {

    /**
     * description: 新增
     *
     * @param tag 要保存的实例
     * @return net.xiaosaguo.blog.po.Tag 保存成功后的实例
     * @author xiaosaguo
     * @date 2020-04-28 05:29
     */
    Tag save(Tag tag);

    /**
     * description: 根据条件分页查询
     *
     * @param pageable 分页参数
     * @return org.springframework.data.domain.Page<net.xiaosaguo.blog.po.Tag> 查询到的实例列表，封装进分页信息中
     * @author xiaosaguo
     * @date 2020-04-28 05:32
     */
    Page<Tag> list(Pageable pageable);

    /**
     * description: 根据主键查询
     *
     * @param id tag
     * @return net.xiaosaguo.blog.po.Tag 根据主键查询到的对象
     * @author xiaosaguo
     * @date 2020-04-28 05:27
     */
    Tag get(Long id);

    /**
     * description: 根据主键更新
     *
     * @param id  标签的id
     * @param tag 要修改的信息
     * @return net.xiaosaguo.blog.po.Tag 更新成功后的对象信息
     * @author xiaosaguo
     * @date 2020-04-30 09:52
     */
    Tag update(Long id, Tag tag);

    /**
     * description: 根据主键删除
     *
     * @param id 主键
     * @author xiaosaguo
     * @date 2020-04-30 10:18
     */
    void delete(Long id);

    /**
     * description: 根据名称查询
     *
     * @param name 标签名称
     * @return net.xiaosaguo.blog.po.Tag
     * @author xiaosaguo
     * @date 2020-04-30 19:41
     */
    Tag getByName(String name);

}
