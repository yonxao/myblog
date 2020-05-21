package net.xiaosaguo.blog.po;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 标签Entity
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Data
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 20, message = "标签名称不能超过20个字符")
    @NotBlank(message = "标签名称不能为空")
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();
}
