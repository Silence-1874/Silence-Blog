package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.TagDO;
import top.silence.service.TagService;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag/{pageNum}/{pageSize}")
    public Result page(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<TagDO> pages = tagService.page(pageNum, pageSize);
        return Result.ok("成功获得标签分页信息", pages);
    }

    @SaCheckRole("admin")
    @PostMapping("/tag")
    public Result add(@RequestBody TagDO tagDO) {
        tagService.save(tagDO);
        Long newId = tagDO.getId();
        return Result.ok("成功添加标签", newId);
    }

    @SaCheckRole("admin")
    @PutMapping("/tag")
    public Result update(@RequestBody TagDO tagDO) {
        tagService.updateById(tagDO);
        return Result.ok("成功修改标签", tagDO.getId());
    }

    @SaCheckRole("admin")
    @DeleteMapping("/tag/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        tagService.removeById(id);
        return Result.ok("成功删除标签", id);
    }

    @GetMapping("/tag/{id}")
    public Result getById(@PathVariable("id") Long id) {
        TagDO tag = tagService.getById(id);
        if (tag == null) {
            return Result.fail("查询标签失败");
        } else {
            return Result.ok("成功获得标签", tag);
        }
    }

}
