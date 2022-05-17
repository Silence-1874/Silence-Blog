package top.silence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.silence.entity.CategoryDO;
import top.silence.entity.TagDO;

import java.util.Date;
import java.util.List;

/**
 * 博客信息，包括其下的分类对象及标签对象列表
 * @author Silence
 * @date 2022/5/17 11:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 首图url
     */
    private String url;

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
     * 是否置顶
     */
    private Boolean isTop;

    /**
     * 分类
     */
    private CategoryDO category;

    /**
     * 标签列表
     */
    private List<TagDO> tagList;

    /**
     * 分页总数
     */
    private Long totalPage;

}
