package top.silence.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.CategoryDO;
import top.silence.service.CategoryService;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public Result list() {
        List<CategoryDO> list = categoryService.list();
        return Result.ok("成功获得分类列表", list);
    }

    @GetMapping("/category/{id}")
    public Result getById(@PathVariable("id") Long id) {
        CategoryDO category = categoryService.getById(id);
        return Result.ok("成功获得分类", category);
    }

    @GetMapping("/categoryName/{name}")
    public Result getByName(@PathVariable("name") String name) {
        QueryWrapper<CategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", name);
        CategoryDO category = categoryService.getOne(queryWrapper);
        return Result.ok("成功获得分类", category);
    }

}
