package top.silence.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息
 * @author Silence
 * @date 2022/5/15 9:54
 */
@TableName("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {

    @TableId
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色
     */
    private String role;

    /**
     * 创建时间
     */
    private Date createTime;

}
