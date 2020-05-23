package net.xiaosaguo.myblog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: 博客列表查询参数
 *
 * @author xiaosaguo
 * @date 2020/05/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {
    private String title;
    private Long typeId;
    private boolean recommend;
}
