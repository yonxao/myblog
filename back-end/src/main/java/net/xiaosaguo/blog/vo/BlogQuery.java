package net.xiaosaguo.blog.vo;

import lombok.Data;

/**
 * description: 博客列表查询参数
 *
 * @author xiaosaguo
 * @date 2020/05/12
 */
@Data
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
}
