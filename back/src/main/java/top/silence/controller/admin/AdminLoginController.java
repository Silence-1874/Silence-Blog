package top.silence.controller.admin;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.LoginDTO;
import top.silence.dto.Result;
import top.silence.service.UserService;

@RestController
public class AdminLoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = DigestUtil.md5Hex(loginDTO.getPassword());

        if (password.equals(userService.getPassword(username))) {
            // 标记当前会话登录的帐号Id
            StpUtil.login(username);
            return Result.ok("登录成功", StpUtil.getTokenInfo());
        } else {
            return Result.fail("用户名或密码错误");
        }
    }

}
