package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
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

import javax.websocket.server.PathParam;
import java.util.List;

@SaCheckLogin
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

    @GetMapping("/blog/all")
    public Result list() {
        List<BlogDO> list = blogService.list();
        return Result.ok("成功获得所有博客信息", list);
    }

    @GetMapping("/blog")
    public Result pageByQuery(@PathParam("pageNum")Integer pageNum, @PathParam("pageSize") Integer pageSize,
                           @PathParam("categoryId") Long categoryId, @PathParam("title") String title) {
        Page<BlogDO> pages = blogService.pageByQuery(pageNum, pageSize, categoryId, title);
        return Result.ok("成功获得博客分页信息", pages);
    }

    @GetMapping("/blog/{id}")
    public Result getById(@PathVariable("id") Long id) {
        BlogDO blogDO = blogService.getById(id);
        return Result.ok("成功获得博客信息", blogDO);
    }

    @SaCheckRole("admin")
    @PostMapping("/blog")
    public Result add(@RequestBody BlogWriteDTO blogWriteDTO) {
        Long ret = blogService.add(blogWriteDTO);
        return Result.ok("成功发表新博文", ret);
    }

    @SaCheckRole("admin")
    @PutMapping("/blog/{id}")
    public Result updateById(@RequestBody BlogWriteDTO blogWriteDTO, @PathVariable("id") Long id) {
        Long ret = blogService.updateById(blogWriteDTO, id);
        return Result.ok("成功修改博文", ret);
    }

    @GetMapping("/category_tag")
    public Result getCategoryAndTag() {
        List<CategoryDO> categoryList = categoryService.list();
        List<TagDO> tagList = tagService.list();
        return Result.ok("成功获得现有分类和标签", new CategoryAndTagDTO(categoryList, tagList));
    }

    @GetMapping("/blogTag/{blogId}")
    public Result listTagIdByBlogId(@PathVariable("blogId") Long blogId) {
        List<Long> tagList = blogTagService.listTagIdByBlogId(blogId);
        return Result.ok("成功获得当前博客下的标签Id", tagList);
    }

    @SaCheckRole("admin")
    @PutMapping("/blog/top/{id}")
    public Result switchTopById(@PathVariable("id") Long id) {
        BlogDO blog = blogService.getById(id);
        blog.setIsTop(!blog.getIsTop());
        blogService.updateById(blog);
        return Result.ok("成功切换置顶状态");
    }

    @SaCheckRole("admin")
    @DeleteMapping("/blog/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        blogService.deleteById(id);
        return Result.ok("成功删除博客");
    }

}
