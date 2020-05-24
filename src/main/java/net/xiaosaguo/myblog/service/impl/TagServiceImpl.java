package net.xiaosaguo.myblog.service.impl;

import net.xiaosaguo.myblog.dao.TagRepository;
import net.xiaosaguo.myblog.exception.NotFoundException;
import net.xiaosaguo.myblog.pojo.entity.Tag;
import net.xiaosaguo.myblog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 标签 ServiceImpl
 *
 * @author xiaosaguo
 * @date 2020/04/28
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
    public Tag get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("该标签不存在"));
    }

    @Override
    public Tag getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Page<Tag> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Tag> list() {
        return repository.findAll();
    }

    @Override
    public List<Tag> listByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return null;
        }
        return repository.findAllById(strConvertToLongList(ids));
    }

    @Override
    public List<Tag> listTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return repository.findTop(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Tag update(Long id, Tag tag) {
        Tag t = get(id);
        BeanUtils.copyProperties(tag, t);
        t.setId(id);
        return repository.save(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * description: 用逗号拼接数字的字符串转换为 {@code List<Long>}
     *
     * @param str 用逗号拼接数字的字符串，eg： 1,2,3
     * @return 类型为 {@code Long} 的主键集合
     * @author xiaosaguo
     * @date 2020/05/14 06:22
     */
    private List<Long> strConvertToLongList(String str) {
        List<Long> longList = new ArrayList<>();
        if (!StringUtils.isEmpty(str)) {
            String[] strArr = str.split(",");
            for (String s : strArr) {
                longList.add(Long.valueOf(s));
            }
        }
        return longList;
    }
}
