package top.silence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 浏览信息
 * @author Silence
 * @date 2022/8/20 20:42
 */
@TableName("browser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrowserDO {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    Long id;

    /**
     * 访客ip
     */
    String ip;

    /**
     * 浏览的页面
     */
    String page;

    /**
     * 请求url
     */
    String url;

    /**
     * 浏览时间
     */
    Date time;

}
