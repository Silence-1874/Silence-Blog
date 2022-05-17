package top.silence.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.BlogDTO;
import top.silence.dto.Result;
import top.silence.entity.BlogDO;
import top.silence.service.BlogService;
import top.silence.service.BlogTagService;
import top.silence.service.CategoryService;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogCotroller {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/blog")
    public Result listBlog(@PathParam("pageNum")Integer pageNum, @PathParam("pageSize") Integer pageSize) {
        Page<BlogDO> pages = blogService.searchBlog(pageNum, pageSize, null, null);
        List<BlogDO> list = pages.getRecords();
        List<BlogDTO> newList = new ArrayList<>(list.size());

        // 将每个BlogDO转为BlogDTO
        for (BlogDO blog : list) {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtil.copyProperties(blog, blogDTO);
            blogDTO.setTotalPage(pages.getPages());
            blogDTO.setCategory(categoryService.getById(blog.getCategoryId()));
            blogDTO.setTagList(blogTagService.getTagListByBlogId(blog.getId()));
            newList.add(blogDTO);
        }

        return Result.ok("成功获得博客分页信息", newList);
    }

}
