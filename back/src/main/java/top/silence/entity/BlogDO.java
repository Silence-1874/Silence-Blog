package top.silence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 博文信息
 * @author Silence
 * @date 2022/5/12 7:55
 */
@TableName("blog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDO {

    /**
     * 主键Id
     */
    @TableId(type=IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 简介
     */
    private String description;

    /**
     * 正文
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 阅读数
     */
    private Integer views;

    /**
     * 字数
     */
    private Integer words;

    /**
     * 阅读时间（字数/180）
     */
    private Integer readTime;

    /**
     * 分类Id
     */
    private Long categoryId;

    /**
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 是否已删除
     */
    @TableLogic
    private Integer isDeleted;

}
