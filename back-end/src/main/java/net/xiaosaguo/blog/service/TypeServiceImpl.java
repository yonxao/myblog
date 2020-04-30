package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.TypeRepository;
import net.xiaosaguo.blog.exception.NotFoundException;
import net.xiaosaguo.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Type update(Long id, Type type) {
        Type t = typeRepository.findById(id).orElseThrow(() -> new NotFoundException("该记录不存在, id = :" + id));
        type.setId(null);
        BeanUtils.copyProperties(type, t);
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
