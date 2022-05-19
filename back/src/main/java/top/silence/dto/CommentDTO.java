package top.silence.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    /**
     * 主键Id
     */
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
     * 此评论的父评论Id（根评论为-1）
     */
    private Long  parentCommentId;

    /**
     * 博主标识
     */
    private Boolean isAdmin;


    /**
     * 此评论的父评论昵称
     */
    private String parentCommentNickname;

    /**
     * 回复该评论的评论
     */
    private List<CommentDTO> replyComments = new ArrayList<>();

}