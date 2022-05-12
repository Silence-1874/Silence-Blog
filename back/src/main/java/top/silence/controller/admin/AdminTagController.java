package top.silence.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.TagDO;
import top.silence.service.TagService;

@RestController
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag/{pageNum}/{pageSize}")
    private Result listTag(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<TagDO> pages = tagService.listTag(pageNum, pageSize);
        return Result.ok("成功获得标签分页信息", pages);
    }

}
