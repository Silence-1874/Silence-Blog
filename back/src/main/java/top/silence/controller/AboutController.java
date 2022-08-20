package top.silence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.AboutDO;
import top.silence.service.AboutService;

@RestController
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @GetMapping("/about")
    public Result get() {
        AboutDO about = aboutService.getById(1);
        return Result.ok("'关于我'请求成功", about);
    }

}
