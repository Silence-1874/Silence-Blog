package top.silence.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.BlogDO;
import top.silence.service.BlogService;

@RestController
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blog/{pageNum}/{pageSize}")
    private Result listBlog(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<BlogDO> pages = blogService.listBlog(pageNum, pageSize);
        return Result.ok("成功获得博客分页信息", pages);
    }

}
