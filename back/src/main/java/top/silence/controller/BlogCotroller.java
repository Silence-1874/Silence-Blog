package top.silence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.BlogDTO;
import top.silence.dto.Result;
import top.silence.service.BlogService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class BlogCotroller {

    @Autowired
    private BlogService blogService;

    @GetMapping("/blog")
    public Result listBlog(@PathParam("pageNum") Integer pageNum,
                           @PathParam("pageSize") Integer pageSize,
                           @PathParam("categoryId") Long categoryId) {
        List<BlogDTO> list = blogService.listBlog(pageNum, pageSize, categoryId);
        return Result.ok("成功获得博客分页数据", list);
    }

    @GetMapping("/blogInTag")
    public Result listTagInTag(@PathParam("pageNum") Integer pageNum,
                               @PathParam("pageSize") Integer pageSize,
                               @PathParam("tagId") Long tagId) {
        List<BlogDTO> list = blogService.listBlogInTag(pageNum, pageSize, tagId);
        return Result.ok("成功获得当前标签下的博客", list);
    }

    @GetMapping("/countBlog")
    private Result countBlog() {
        return Result.ok("成功获得文章总数", blogService.count());
    }

}
