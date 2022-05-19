package top.silence.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("visitor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDO {

    /**
     * 主键Id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 访客浏览量
     */
    private Integer pv;

    /**
     * 访客ip
     */
    private String ip;

    /**
     * 访客城市
     */
    private String city;

    /**
     * 首次访问时间
     */
    private Date firstTime;

    /**
     * 最后一次访问时间
     */
    private Date lastTime;
}
