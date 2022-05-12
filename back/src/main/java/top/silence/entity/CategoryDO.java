package top.silence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类信息
 * @author Silence
 * @date 2022/5/12 15:01
 */
@TableName("category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDO {

    /**
     * 主键Id
     */
    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String categoryName;

}
