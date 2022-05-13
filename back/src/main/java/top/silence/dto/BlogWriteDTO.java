package top.silence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.silence.entity.CategoryDO;
import top.silence.entity.TagDO;

import java.util.List;

/**
 * 查询或修改时的博客对象
 * @author Silence
 * @date 2022/5/12 21:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogWriteDTO {

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
     * 分类
     */
    private CategoryDO category;

    /**
     * 标签列表
     */
    List<TagDO> tagList;

}
