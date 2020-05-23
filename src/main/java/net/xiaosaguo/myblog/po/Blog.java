package net.xiaosaguo.myblog.po;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: 博客Entity
 *
 * @author xiaosaguo
 * @date 2020/5/1
 */
@Data
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 60, message = "博客标题不能超过30个字符")
    @NotBlank(message = "博客标题不能为空")
    private String title;

    /**
     * description: 博客内容必须是打文本类型，这里设置懒加载，不使用不查询
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotBlank(message = "博客内容不能为空")
    private String content;

    @NotBlank(message = "封面图片地址不能为空")
    private String firstPicture;

    /**
     * description: 博客类型 ： 原创、转载、翻译
     */
    @Pattern(regexp = "^原创$|^转载$|^翻译$", message = "博客类型值有误")
    @NotBlank(message = "博客类型不能为空")
    private String flag;

    @Min(value = 0, message = "浏览次数不能为负数")
    private Integer views;

    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentable;
    private boolean published;
    private boolean recommend;

    @Length(max = 255, message = "博客描述不能超过255个字符")
    @NotBlank(message = "博客描述不能为空")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    @NotNull(message = "博客分类不能为空")
    @ManyToOne
    private Type type;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    /**
     * description: 处理 tagIds ，直接处理成要用的形式 eg：1,2,3
     *
     * @return 将 {@literal id} 用 {@literal ,} 拼接后的字符串
     * @author xiaosaguo
     * @date 2020/05/14 21:52
     */
    public String getTagIds() {
        if (tagIds != null) {
            return tagIds;
        }
        List<Long> idList = new ArrayList<>();
        tags.forEach(tag -> idList.add(tag.getId()));
        return StringUtils.collectionToDelimitedString(idList, ",");
    }
}
