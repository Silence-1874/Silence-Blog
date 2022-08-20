package top.silence.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.TagDO;
import top.silence.service.TagService;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tag")
    public Result list() {
        List<TagDO> list = tagService.list();
        return Result.ok("成功获得标签列表", list);
    }

    @GetMapping("/tagName/{name}")
    public Result getByName(@PathVariable("name") String name) {
        QueryWrapper<TagDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("tag_name", name);
        TagDO tag = tagService.getOne(queryWrapper);
        return Result.ok("成功获得标签", tag);
    }

}
