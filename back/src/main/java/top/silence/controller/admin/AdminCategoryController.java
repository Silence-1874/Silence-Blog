package top.silence.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.entity.CategoryDO;
import top.silence.service.CategoryService;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/category/{pageNum}/{pageSize}")
    private Result listBlog(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<CategoryDO> pages = categoryService.listCategory(pageNum, pageSize);
        return Result.ok("成功获得分类分页信息", pages);
    }

}
