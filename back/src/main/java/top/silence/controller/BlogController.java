package top.silence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.BlogDTO;
import top.silence.dto.Result;
import top.silence.entity.BlogDO;
import top.silence.service.BlogService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    public Result page(@PathParam("pageNum") Integer pageNum,
                           @PathParam("pageSize") Integer pageSize,
                           @PathParam("categoryId") Long categoryId) {
        List<BlogDTO> list = blogService.page(pageNum, pageSize, categoryId);
        return Result.ok("成功获得博客分页数据", list);
    }

    @GetMapping("/blog/{id}")
    public Result getDtoById(@PathVariable("id") Long id) {
        BlogDTO blogDTO = blogService.getDtoById(id);
        return Result.ok("成功获得博客数据", blogDTO);
    }

    @GetMapping("/blogInTag")
    public Result pageByTagId(@PathParam("pageNum") Integer pageNum,
                               @PathParam("pageSize") Integer pageSize,
                               @PathParam("tagId") Long tagId) {
        List<BlogDTO> list = blogService.pageByTagId(pageNum, pageSize, tagId);
        return Result.ok("成功获得当前标签下的博客", list);
    }

    @GetMapping("/countBlog")
    public Result count() {
        return Result.ok("成功获得文章总数", blogService.count());
    }

    @GetMapping("/increaseViewById/{id}")
    public Result increaseViewById(@PathVariable("id") Long id) {
        BlogDO blog = blogService.getById(id);
        blog.setViews(blog.getViews() + 1);
        blogService.updateById(blog);
        return Result.ok("浏览量+1");
    }

}
