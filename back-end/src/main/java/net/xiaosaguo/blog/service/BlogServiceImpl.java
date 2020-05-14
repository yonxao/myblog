package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.BlogRepository;
import net.xiaosaguo.blog.exception.NotFoundException;
import net.xiaosaguo.blog.po.Blog;
import net.xiaosaguo.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


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
    public Page<Blog> list(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll((Specification<Blog>) (root, query, criteriaBuilder) -> {
            // TODO 学习 lambda 表达式用法，学习匿名内部类，学习数组操作
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(blogQuery.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + blogQuery.getTitle() + "%"));
            }
            if (!StringUtils.isEmpty(blogQuery.getTypeId())) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"), blogQuery.getTypeId()));
            }
            if (blogQuery.isRecommend()) {
                predicates.add(criteriaBuilder.equal(root.get("recommend"), blogQuery.isRecommend()));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog save(Blog blog) {
        blog.setId(null);
        blog.setViews(0);
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
