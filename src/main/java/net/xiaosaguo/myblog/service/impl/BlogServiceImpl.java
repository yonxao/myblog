package net.xiaosaguo.myblog.service.impl;

import net.xiaosaguo.myblog.dao.BlogRepository;
import net.xiaosaguo.myblog.exception.NotFoundException;
import net.xiaosaguo.myblog.pojo.entity.Blog;
import net.xiaosaguo.myblog.pojo.query.BlogListSearchQuery;
import net.xiaosaguo.myblog.service.BlogService;
import net.xiaosaguo.myblog.util.MarkdownUtils;
import net.xiaosaguo.myblog.util.MyBeanUtils;
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
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * description: 博客 ServiceImpl
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
        return blogRepository.findById(id).orElseThrow(() -> new NotFoundException("该博客不存在"));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog getAndConvert(long id) {
        Blog blog = get(id);
        // 不能修改原对象，否则 hibernate sql session 会将改变持久化到数据库中
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // 浏览次数累加
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> list(Pageable pageable, BlogListSearchQuery searchQuery) {
        return blogRepository.findAll((Specification<Blog>) (root, query, criteriaBuilder) -> {
            // TODO 学习 lambda 表达式用法，学习匿名内部类，学习数组操作
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(searchQuery.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + searchQuery.getTitle() + "%"));
            }
            if (!StringUtils.isEmpty(searchQuery.getTypeId())) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"), searchQuery.getTypeId()));
            }
            if (searchQuery.isRecommend()) {
                predicates.add(criteriaBuilder.equal(root.get("recommend"), searchQuery.isRecommend()));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return null;
        }, pageable);
    }

    @Override
    public Page<Blog> list(Long tagId, Pageable pageable) {
        // 此处先不转换为 lambda 表达式，和上面的方法做个语法对比
        return blogRepository.findAll(new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Object, Object> join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"), tagId);
            }
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

    @Override
    public Map<String, List<Blog>> archivesBlog() {
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        List<String> yearList = blogRepository.findYearList();
        yearList.forEach(year -> map.put(year, blogRepository.findByYear(year)));
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
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
