package top.silence.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.BlogWriteDTO;
import top.silence.dto.CategoryAndTagDTO;
import top.silence.dto.Result;
import top.silence.entity.BlogDO;
import top.silence.entity.CategoryDO;
import top.silence.entity.TagDO;
import top.silence.service.BlogService;
import top.silence.service.CategoryService;
import top.silence.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blog/{pageNum}/{pageSize}")
    private Result listBlog(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<BlogDO> pages = blogService.listBlog(pageNum, pageSize);
        return Result.ok("成功获得博客分页信息", pages);
    }

    @PostMapping("/blog")
    private Result addBlog(@RequestBody BlogWriteDTO blogWriteDTO) {
        Long ret = blogService.saveBlog(blogWriteDTO);
        return Result.ok("成功发表新博文", ret);
    }

    @GetMapping("/category_tag")
    private Result getCategoryAndTag() {
        List<CategoryDO> categoryList = categoryService.list();
        List<TagDO> tagList = tagService.list();
        return Result.ok("成功获得现有分类和标签", new CategoryAndTagDTO(categoryList, tagList));
    }


}
