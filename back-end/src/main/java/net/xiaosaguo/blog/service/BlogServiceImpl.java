package net.xiaosaguo.blog.service;

import net.xiaosaguo.blog.dao.BlogRepository;
import net.xiaosaguo.blog.exception.NotFoundException;
import net.xiaosaguo.blog.po.Blog;
import net.xiaosaguo.blog.util.MarkdownUtils;
import net.xiaosaguo.blog.util.MyBeanUtils;
import net.xiaosaguo.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog save(Blog blog) {
        blog.setId(null);
        blog.setViews(0);
        return blogRepository.save(blog);
    }

    @Override
    public Blog get(long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Blog getAndConvert(long id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("该博客不存在"));
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        return b;
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

    @Override
    public Page<Blog> list(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> listRecommendTop(int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findRecommendTop(pageable);
    }

    @Override
    public List<Blog> listTop(int size, Sort sort) {
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> likeTitleOrContent(String keyword, Pageable pageable) {
        return blogRepository.likeTitleOrContent(keyword, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog update(long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("不存在该记录：id = " + id));
        // 如果blog中的属性值为null，就不覆盖
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(long id) {
        blogRepository.deleteById(id);
    }
}
