package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.BrowserDO;
import top.silence.service.BrowserService;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminBrowserController {
    @Autowired
    private BrowserService browserService;

    @GetMapping("/browser/{pageNum}/{pageSize}")
    public Result page(@PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Page<BrowserDO> page = new Page<>(pageNum, pageSize);
        page.addOrder(OrderItem.desc("time"));
        return Result.ok("成功获得浏览列表", browserService.page(page));
    }
}
