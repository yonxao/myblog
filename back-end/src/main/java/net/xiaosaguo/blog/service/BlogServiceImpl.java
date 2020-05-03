package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.BlogRepository;
import net.xiaosaguo.blog.exception.NotFoundException;
import net.xiaosaguo.blog.po.Blog;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * description: 博客ServiceImpl
 *
 * @author xiaosaguo
 * @date 2020/05/02
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogRepository blogRepository;

    @Override
    public Blog get(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Blog> list(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog save(Blog blog) {
        blog.setId(null);
        return blogRepository.save(blog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog update(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("不存在该记录：id = " + id));
        BeanUtils.copyProperties(blog, b);
        b.setId(id);
        return blogRepository.save(b);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
