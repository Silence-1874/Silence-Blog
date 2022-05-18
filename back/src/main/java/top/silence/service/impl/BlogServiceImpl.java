package top.silence.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.dto.ArchiveDTO;
import top.silence.dto.BlogDTO;
import top.silence.dto.BlogWriteDTO;
import top.silence.entity.BlogDO;
import top.silence.entity.CategoryDO;
import top.silence.entity.TagDO;
import top.silence.mapper.BlogMapper;
import top.silence.service.BlogService;
import top.silence.service.BlogTagService;
import top.silence.service.CategoryService;
import top.silence.service.TagService;

import java.util.*;
import java.util.stream.Collectors;

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
    public Page<BlogDO> searchBlog(Integer pageNum, Integer pageSize, Long categoryId, String title) {
        Page<BlogDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BlogDO> queryWrapper= new QueryWrapper<>();
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (StrUtil.isNotBlank(title)) {
            queryWrapper.like("title", title);
        }
        queryWrapper.orderByDesc("is_top");
        queryWrapper.orderByDesc("create_time");
        queryWrapper.orderByDesc("update_time");
        return blogMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<BlogDTO> listBlog(Integer pageNum, Integer pageSize, Long categoryId) {
        Page<BlogDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<BlogDO> queryWrapper= new QueryWrapper<>();
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        queryWrapper.orderByDesc("is_top");
        queryWrapper.orderByDesc("create_time");
        queryWrapper.orderByDesc("update_time");
        Page<BlogDO> pages = blogMapper.selectPage(page, queryWrapper);


        List<BlogDO> list = pages.getRecords();
        List<BlogDTO> newList = new ArrayList<>(list.size());

        // 将每个BlogDO转为BlogDTO
        for (BlogDO blog : list) {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtil.copyProperties(blog, blogDTO);
            blogDTO.setTotalPage(pages.getPages());
            blogDTO.setTotalRecords(pages.getTotal());
            blogDTO.setCategory(categoryService.getById(blog.getCategoryId()));
            blogDTO.setTagList(blogTagService.getTagListByBlogId(blog.getId()));
            newList.add(blogDTO);
        }
        return newList;
    }

    @Override
    public BlogDTO getBlogDTOById(Long id) {
        BlogDO blog = blogMapper.selectById(id);
        BlogDTO blogDTO = new BlogDTO();
        BeanUtil.copyProperties(blog, blogDTO);
        blogDTO.setCategory(categoryService.getById(blog.getCategoryId()));
        blogDTO.setTagList(blogTagService.getTagListByBlogId(blog.getId()));
        return blogDTO;
    }

    @Override
    public List<BlogDTO> listBlogInTag(Integer pageNum, Integer pageSize, Long tagId) {
        Page<BlogDO> page = new Page<>(pageNum, pageSize);

        List<BlogDO> list = blogMapper.pageList(page, tagId);
        List<BlogDTO> newList = new ArrayList<>(list.size());

        Long total = blogMapper.countBlogInTag(tagId);
        // 将每个BlogDO转为BlogDTO
        for (BlogDO blog : list) {
            BlogDTO blogDTO = new BlogDTO();
            BeanUtil.copyProperties(blog, blogDTO);
            blogDTO.setTotalPage(total / pageSize + 1);
            blogDTO.setTotalRecords(total);
            blogDTO.setCategory(categoryService.getById(blog.getCategoryId()));
            blogDTO.setTagList(blogTagService.getTagListByBlogId(blog.getId()));
            newList.add(blogDTO);
        }
        return newList;
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

    @Override
    public Long updateBlog(BlogWriteDTO blogWriteDTO, Long id) {
        BlogDO blog = getById(id);
        blog.setTitle(blogWriteDTO.getTitle());
        blog.setUrl(blogWriteDTO.getUrl());
        blog.setContent(blogWriteDTO.getContent());
        CategoryDO blogCategory = blogWriteDTO.getCategory();
        List<TagDO> blogTagList = blogWriteDTO.getTagList();

        // 插入新分类
        if (blogCategory.getId() == null) {
            categoryService.save(blogCategory);
        }
        // 更新分类
        blog.setCategoryId(blogCategory.getId());

        // 更新时间
        blog.setUpdateTime(new Date());

        // 更新字数及阅读时间
        Integer words  = blogWriteDTO.getContent().length();
        blog.setWords(words);
        blog.setReadTime(words / 180);

        // 插入新标签
        for (TagDO blogTag : blogTagList) {
            if (blogTag.getId() == null) {
                tagService.save(blogTag);
            }
        }
        // 更新标签
        List<Long> newList = blogWriteDTO.getTagList().stream().map(TagDO::getId).collect(Collectors.toList());
        List<Long> oldList = blogTagService.getTagIdListByBlogId(id);
        updateTagList(id, newList, oldList);
        blogMapper.updateById(blog);
        return null;
    }

    @Override
    public void deleteBlog(Long id) {
        BlogDO blog = blogMapper.selectById(id);

        // 更新修改时间
        blog.setUpdateTime(new Date());
        blogMapper.updateById(blog);

        // 删除该博客所有的标签映射信息
        List<Long> tagIdList = blogTagService.getTagIdListByBlogId(id);
        for (Long tagId : tagIdList) {
            blogTagService.deleteBlogTag(id, tagId);
        }

        // TODO: 删除博客下所有的评论

        blogMapper.deleteById(blog);
    }

    private void updateTagList(Long blogId, List<Long> newList, List<Long> oldList) {
        Set<Long> newSet = new HashSet<>(newList);
        Set<Long> oldSet = new HashSet<>(oldList);
        // 删除标签
        for (Long id : oldSet) {
            if (!newSet.contains(id)) {
                blogTagService.deleteBlogTag(blogId, id);
            }
        }
        // 新增标签
        for (Long id : newSet) {
            if (!oldSet.contains(id)) {
                blogTagService.insertBlogTag(blogId, id);
            }
        }
    }

    @Override
    public Map<String, List<ArchiveDTO>> archiveBlog() {
        Map<String, List<ArchiveDTO>> map = new HashMap<>();
        List<BlogDO> blogList = list();
        for (BlogDO blog : blogList) {
            Date createTime = blog.getCreateTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(createTime);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            String key = year + "年" + month + "月";
            String date = calendar.get(Calendar.DATE) + "日";
            ArchiveDTO archiveDTO = new ArchiveDTO(blog.getId(), blog.getTitle(), date);
            map.put(key, map.getOrDefault(key, new ArrayList<>()));
            map.get(key).add(archiveDTO);
        }
        return map;
    }

}
