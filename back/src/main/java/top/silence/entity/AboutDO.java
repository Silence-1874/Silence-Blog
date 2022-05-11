package top.silence.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * "关于我"页面信息
 * @author Silence
 * @date 2022/5/11 15:50
 */
@TableName(value = "about")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutDO implements Serializable {

    /**
     * 主键Id
     */
    @TableId
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

}