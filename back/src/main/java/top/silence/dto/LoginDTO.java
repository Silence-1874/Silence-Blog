package top.silence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录表单信息
 * @author Silence
 * @date 2022/5/15 10:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
