package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.dto.VisitorDO;
import top.silence.service.VisitorService;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminVisitorController {

    @Autowired
    VisitorService visitorService;

    @GetMapping("/visitor/{pageNum}/{pageSize}")
    public Result listVisitor(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Page<VisitorDO> page = new Page<>(pageNum, pageSize);
        return Result.ok("成功获得访客列表", visitorService.page(page));
    }

}
