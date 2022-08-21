package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.VisitorDO;
import top.silence.service.VisitorService;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminVisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/visitor/{pageNum}/{pageSize}")
    public Result page(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Page<VisitorDO> page = new Page<>(pageNum, pageSize);
        page.addOrder(OrderItem.desc("first_time"));
        return Result.ok("成功获得访客列表", visitorService.page(page));
    }

}
