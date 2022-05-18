package top.silence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 评论信息
 * @author Silence
 * @date 2022/5/18 10:10
 */
@TableName("comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDO {

    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 评论者昵称
     */
    private String nickname;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者头像Url
     */
    private String avatar;

    /**
     * 评论创建时间
     */
    private Date createTime;

    /**
     * 评论所属博客的Id
     */
    private Long blogId;

    /**
     * 此评论的父评论Id（父评论为-1）
     */
    private Long  parentCommentId;

    /**
     * 评论者QQ号
     */
    private String qq;

    /**
     * 评论者Ip
     */
    private String ip;

    /**
     * 博主标识
     */
    private Boolean isAdmin;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean isDeleted;

}
