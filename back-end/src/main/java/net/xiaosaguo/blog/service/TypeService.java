package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * description: 分类的相关接口
 *
 * @author xiaosaguo
 */
public interface TypeService {

    /**
     * description: 保存
     *
     * @param type 要保存的对象
     * @return net.xiaosaguo.blog.po.Type 返回保存成功后的对象
     * @author xiaosaguo
     * @date 2020-04-26 05:11
     */
    Type save(Type type);

    /**
     * description: 根据id查询
     *
     * @param id 分类的id
     * @return net.xiaosaguo.blog.po.Type 返回根据id查询到的分类
     * @author xiaosaguo
     * @date 2020-04-26 05:13
     */
    Type get(Long id);

    /**
     * description: 分页查询满足条件的分类
     *
     * @param pageable 分页参数
     * @return org.springframework.data.domain.Page<net.xiaosaguo.blog.po.Type>
     * @author xiaosaguo
     * @date 2020-04-26 05:14
     */
    Page<Type> list(Pageable pageable);

    /**
     * description: 根据id修改分类
     *
     * @param id   分类的id
     * @param type 变动后的分类信息
     * @return net.xiaosaguo.blog.po.Type
     * @author xiaosaguo
     * @date 2020-04-26 05:17
     */
    Type update(Long id, Type type);

    /**
     * description: 根据id删除分类
     *
     * @param id 分类的id
     * @author xiaosaguo
     * @date 2020-04-26 05:18
     */
    void delete(Long id);

    /**
     * description: 根据分类名称查找分类
     *
     * @param name 分类名称
     * @return net.xiaosaguo.blog.po.Type
     * @author xiaosaguo
     * @date 2020-04-26 08:31
     */
    Type getByName(String name);
}
