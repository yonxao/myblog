package net.xiaosaguo.blog.po;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 分类Entity
 *
 * @author xiaosaguo
 * @version 1 xiaosaguo 创建
 */
@Data
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 20, message = "分类名称不能超过20个字符")
    @NotBlank(message = "分类名称不能为空")
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();
}
