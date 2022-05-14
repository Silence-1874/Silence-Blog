package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.AboutDO;
import top.silence.service.AboutService;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminAboutController {

    @Autowired
    private AboutService aboutService;

    @GetMapping("/about")
    private Result getAbout() {
        AboutDO about = aboutService.getById(1);
        return Result.ok("'关于我'请求成功", about);
    }

    @SaCheckRole("admin")
    @PutMapping("/about")
    private Result updateAbout(@RequestBody AboutDO about) {
        aboutService.updateById(about);
        return Result.ok("'关于我'修改成功", about);
    }

}
