package top.silence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.silence.entity.CategoryDO;
import top.silence.entity.TagDO;

import java.util.List;

/**
 * 获得现有的分类和标签
 * @author Silence
 * @date 2022/5/13 8:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAndTagDTO {

    /**
     * 分类列表
     */
    private List<CategoryDO> categoryList;

    /**
     * 标签列表
     */
    private List<TagDO> tagList;

}
