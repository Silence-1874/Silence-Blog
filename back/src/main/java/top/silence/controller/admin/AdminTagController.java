package top.silence.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/tag")
    private Result addTag(@RequestBody TagDO tagDO) {
        tagService.save(tagDO);
        Long newId = tagDO.getId();
        return Result.ok("成功添加标签", newId);
    }

    @PutMapping("/tag")
    private Result updateTag(@RequestBody TagDO tagDO) {
        tagService.updateById(tagDO);
        return Result.ok("成功修改标签", tagDO.getId());
    }

}
