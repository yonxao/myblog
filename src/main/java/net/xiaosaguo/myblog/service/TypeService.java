package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.pojo.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * description: 分类 Service
 *
 * @author xiaosaguo
 * @date 2020/04/26
 */
public interface TypeService {

    /**
     * description: 保存
     *
     * @param type 要保存的 {@code Type}
     * @return 返回保存成功后的 {@code Type}
     * @author xiaosaguo
     * @date 2020-04-26 05:11
     */
    Type save(Type type);

    /**
     * description: 根据 ID 查询
     *
     * @param id {@code Type} 的 ID
     * @return 返回根据 ID 查询到的 {@code Type}
     * @author xiaosaguo
     * @date 2020-04-26 05:13
     */
    Type get(Long id);

    /**
     * description: 根据分类名称查找分类
     *
     * @param name 分类名称
     * @return 返回根据分类名称查询到的 {@code Type}
     * @author xiaosaguo
     * @date 2020-04-26 08:31
     */
    Type getByName(String name);

    /**
     * description: 分页查询满足条件的分类
     *
     * @param pageable 分页参数
     * @return {@code Page<Type>} 根据条件查询出的包装了分页信息的结果集
     * @author xiaosaguo
     * @date 2020-04-26 05:14
     */
    Page<Type> list(Pageable pageable);

    /**
     * description: 查询所有分类
     *
     * @return 返回所有 @{code Type}
     * @author xiaosaguo
     * @date 2020/05/12 07:26
     */
    List<Type> list();

    /**
     * description: 根据分类下的博客数量排名，返回前几名
     *
     * @param size 前几个
     * @return 前 {@code size} 名的 {@code Type}
     * @author xiaosaguo
     * @date 2020/05/15 23:14
     */
    List<Type> listTop(Integer size);

    /**
     * description: 根据 ID 修改分类
     *
     * @param id   {@code Type} 的 ID
     * @param type 变动后的分类信息
     * @return 返回更新成功后的 {@code Type}
     * @author xiaosaguo
     * @date 2020-04-26 05:17
     */
    Type update(Long id, Type type);

    /**
     * description: 根据 ID 删除分类
     *
     * @param id {@code Type} 的 ID
     * @author xiaosaguo
     * @date 2020-04-26 05:18
     */
    void delete(Long id);
}
