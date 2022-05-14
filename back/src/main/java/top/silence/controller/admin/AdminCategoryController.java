package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.CategoryDO;
import top.silence.service.CategoryService;

@SaCheckLogin
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

    @SaCheckRole("admin")
    @PostMapping("/category")
    private Result addCategory(@RequestBody CategoryDO categoryDO) {
        categoryService.save(categoryDO);
        Long newId = categoryDO.getId();
        return Result.ok("成功添加分类", newId);
    }

    @SaCheckRole("admin")
    @DeleteMapping("/category/{id}")
    private Result deleteCategory(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return Result.ok("成功删除分类", id);
    }

    @GetMapping("/category")
    private Result listAllCategory() {
        return Result.ok("成功查询所有分类", categoryService.list());
    }

    @GetMapping("/category/{id}")
    private Result getCategoryById(@PathVariable("id") Long id) {
        CategoryDO category = categoryService.getById(id);
        if (category == null) {
            return Result.fail("查询分类失败");
        } else {
            return Result.ok("成功获得分类", category);
        }
    }

    @SaCheckRole("admin")
    @PutMapping("/category")
    private Result updateCategory(@RequestBody CategoryDO categoryDO) {
        categoryService.updateById(categoryDO);
        return Result.ok("成功修改分类", categoryDO.getId());
    }

}
