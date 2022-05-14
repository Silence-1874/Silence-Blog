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
import top.silence.service.BlogTagService;
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

    @Autowired
    private BlogTagService blogTagService;

    @GetMapping("/blog/{pageNum}/{pageSize}")
    private Result listBlog(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Page<BlogDO> pages = blogService.listBlog(pageNum, pageSize);
        return Result.ok("成功获得博客分页信息", pages);
    }

    @GetMapping("/blog_category/{categoryId}/{pageNum}/{pageSize}")
    public Result listBlogByCategory(@PathVariable("categoryId") Long categoryId,
                                     @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Page<BlogDO> pages = blogService.listPageByCategoryId(categoryId, pageNum, pageSize);
        return Result.ok("成功获得当前分类下的博客分页信息", pages);
    }

    @GetMapping("/blog/{id}")
    private Result getBlogById(@PathVariable("id") Long id) {
        BlogDO blogDO = blogService.getById(id);
        return Result.ok("成功获得博客信息", blogDO);
    }

    @PostMapping("/blog")
    private Result addBlog(@RequestBody BlogWriteDTO blogWriteDTO) {
        Long ret = blogService.saveBlog(blogWriteDTO);
        return Result.ok("成功发表新博文", ret);
    }

    @PutMapping("/blog/{id}")
    private Result updateBlog(@RequestBody BlogWriteDTO blogWriteDTO, @PathVariable("id") Long id) {
        Long ret = blogService.updateBlog(blogWriteDTO, id);
        return Result.ok("成功修改博文", ret);
    }

    @GetMapping("/category_tag")
    private Result getCategoryAndTag() {
        List<CategoryDO> categoryList = categoryService.list();
        List<TagDO> tagList = tagService.list();
        return Result.ok("成功获得现有分类和标签", new CategoryAndTagDTO(categoryList, tagList));
    }

    @GetMapping("/blogTag/{blogId}")
    private Result getBlogTag(@PathVariable("blogId") Long blogId) {
        List<Long> tagList = blogTagService.getTagIdListByBlogId(blogId);
        return Result.ok("成功获得当前博客下的标签Id", tagList);
    }

    @PutMapping("/blog/top/{id}")
    public Result switchTop(@PathVariable("id") Long id) {
        BlogDO blog = blogService.getById(id);
        blog.setIsTop(!blog.getIsTop());
        blogService.updateById(blog);
        return Result.ok("成功切换置顶状态");
    }

    @DeleteMapping("/blog/{id}")
    public Result removeBlog(@PathVariable("id") Long id) {
        blogService.deleteBlog(id);
        return Result.ok("成功删除博客");
    }

}
