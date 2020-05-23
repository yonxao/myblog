package net.xiaosaguo.myblog.service;

import net.xiaosaguo.myblog.dao.TypeRepository;
import net.xiaosaguo.myblog.exception.NotFoundException;
import net.xiaosaguo.myblog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * description: 分类的相关接口的实现
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeRepository typeRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Type save(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type get(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Type> list(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> list() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Type update(Long id, Type type) {
        Type t = typeRepository.findById(id).orElseThrow(() -> new NotFoundException("该记录不存在, id = :" + id));
        BeanUtils.copyProperties(type, t);
        t.setId(id);
        /*
         * TODO: 一个比较有意思的点
         * 这里的t如果id为null，就好报异常，而直接调用新增则没问题，
         * 猜想：和请求方式有关，put请求不允许新增资源，只能修改资源
         */
        return typeRepository.save(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getByName(String name) {
        return typeRepository.findByName(name);
    }
}
