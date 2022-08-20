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
    public Result page(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<CategoryDO> pages = categoryService.page(pageNum, pageSize);
        return Result.ok("成功获得分类分页信息", pages);
    }

    @SaCheckRole("admin")
    @PostMapping("/category")
    public Result add(@RequestBody CategoryDO categoryDO) {
        categoryService.save(categoryDO);
        Long newId = categoryDO.getId();
        return Result.ok("成功添加分类", newId);
    }

    @SaCheckRole("admin")
    @DeleteMapping("/category/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return Result.ok("成功删除分类", id);
    }

    @SaCheckRole("admin")
    @PutMapping("/category")
    public Result update(@RequestBody CategoryDO categoryDO) {
        categoryService.updateById(categoryDO);
        return Result.ok("成功修改分类", categoryDO.getId());
    }

}
