package top.silence.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.dto.BlogWriteDTO;
import top.silence.entity.BlogDO;
import top.silence.entity.CategoryDO;
import top.silence.entity.TagDO;
import top.silence.mapper.BlogMapper;
import top.silence.service.BlogService;
import top.silence.service.BlogTagService;
import top.silence.service.CategoryService;
import top.silence.service.TagService;

import java.util.Date;
import java.util.List;

/**
 * @author Silence
 * @date 2022/5/12 8:55
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogDO> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagService blogTagService;

    @Override
    public Page<BlogDO> listBlog(Integer pageNum, Integer pageSize) {
        // 设置分页参数
        Page<BlogDO> page = new Page<>(pageNum, pageSize);
        return blogMapper.selectPage(page, null);
    }

    @Override
    public Long saveBlog(BlogWriteDTO blogWriteDTO) {
        String title = blogWriteDTO.getTitle();
        String url = blogWriteDTO.getUrl();
        String content = blogWriteDTO.getContent();
        Date createTime = new Date();
        Date updateTime = createTime;
        Integer views = 0;
        Boolean isTop = false;
        Integer isDeleted = 0;

        // 计算文章字数以及阅读时间
        Integer words = blogWriteDTO.getContent().length();
        Integer readTime = words / 180;


        CategoryDO blogCategory = blogWriteDTO.getCategory();
        List<TagDO> blogTagList = blogWriteDTO.getTagList();

        // 插入新分类
        if (blogCategory.getId() == null) {
            categoryService.save(blogCategory);
        }

        // 插入新标签
        for (TagDO blogTag : blogTagList) {
            if (blogTag.getId() == null) {
                tagService.save(blogTag);
            }
        }

        // 创建博客
        BlogDO blog = new BlogDO(null, title, url, content, createTime, updateTime, views, words, readTime, blogCategory.getId(), isTop, isDeleted);
        blogMapper.insert(blog);

        // 建立博客与标签的映射关系
        blogTagService.mapBlogTag(blog.getId(), blogWriteDTO.getTagList());
        return blog.getId();
    }

}
