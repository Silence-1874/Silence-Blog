package top.silence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签信息
 * @author Silence
 * @date 2022/5/12 19:53
 */
@TableName("tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDO {

    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 标签颜色
     */
    private String color;

}
