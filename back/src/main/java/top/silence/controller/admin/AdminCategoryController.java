package top.silence.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.CategoryDO;
import top.silence.service.CategoryService;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category/{pageNum}/{pageSize}")
    private Result listCategory(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<CategoryDO> pages = categoryService.listCategory(pageNum, pageSize);
        return Result.ok("成功获得分类分页信息", pages);
    }

    @PostMapping("/category")
    private Result addCategory(@RequestBody CategoryDO categoryDO) {
        categoryService.save(categoryDO);
        Long newId = categoryDO.getId();
        return Result.ok("成功添加分类", newId);
    }

    @DeleteMapping("/category/{id}")
    private Result deleteCategory(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return Result.ok("成功删除分类", id);
    }

}
