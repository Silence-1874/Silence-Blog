package top.silence.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.AboutDO;
import top.silence.service.AboutService;

@RestController
@RequestMapping("/admin")
public class AdminAboutController {

    @Autowired
    private AboutService aboutService;

    @GetMapping("/about")
    private Result getAbout() {
        AboutDO about = aboutService.getById(1);
        return Result.ok(about);
    }

    @PutMapping("/about")
    private Result updateAbout(@RequestBody AboutDO about) {
        aboutService.updateById(about);
        return Result.ok(about);
    }

}
