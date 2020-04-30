package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.TagRepository;
import net.xiaosaguo.blog.exception.NotFoundException;
import net.xiaosaguo.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * description: 标签的相关接口实现
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagRepository repository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag save(Tag tag) {
        tag.setId(null);
        return repository.save(tag);
    }

    @Override
    public Page<Tag> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Tag get(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag update(Long id, Tag tag) {
        Tag t = repository.findById(id).orElseThrow(() -> new NotFoundException("不存在该记录：id = " + id));
        BeanUtils.copyProperties(tag, t);
        t.setId(id);
        return repository.save(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Tag getByName(String name) {
        return repository.findByName(name);
    }
}
