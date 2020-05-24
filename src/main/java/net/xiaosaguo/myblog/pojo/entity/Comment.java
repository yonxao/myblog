package net.xiaosaguo.myblog.pojo.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * description: 评论 Entity
 *
 * @author xiaosaguo
 * @date 2020/04/24
 */
@Data
@Entity
@Table(name = "t_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 30, message = "昵称不能超过30个字符")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    /**
     * description: 内容必须是打文本类型，这里设置懒加载，不使用不查询
     */
    @NotBlank(message = "评论内容不能为空")
    @Lob
    private String content;

    private String avatar;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(updatable = false)
    private Date createTime;

    private boolean owner;

    @ManyToOne
    private Blog blog;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();

    @ManyToOne
    private Comment parentComment;

}
