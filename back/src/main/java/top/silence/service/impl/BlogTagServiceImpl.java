package top.silence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.BlogTagDO;
import top.silence.entity.TagDO;
import top.silence.mapper.BlogTagMapper;
import top.silence.mapper.TagMapper;
import top.silence.service.BlogTagService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Silence
 * @date 2022/5/13 9:34
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTagDO> implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void mapBlogTag(Long blogId, List<TagDO> tagList) {
        List<BlogTagDO> blogTagDOList = blogTagMapper.selectList(null);
        List<Long> list = blogTagDOList.stream()
                .filter(i -> i.getBlogId().equals(blogId))
                .map(BlogTagDO::getTagId)
                .collect(Collectors.toList());

        BlogTagDO blogTagDO = new BlogTagDO(blogId, null);
        for (TagDO tag : tagList) {
            Long tagId = tag.getId();
            if (!list.contains(tagId)) {
                blogTagDO.setTagId(tagId);
                blogTagMapper.insert(blogTagDO);
            }
        }
    }

    @Override
    public List<Long> getTagIdListByBlogId(Long blogId) {
        Map<String, Object> map = new HashMap<>();
        map.put("blog_id", blogId);
        List<BlogTagDO> list = blogTagMapper.selectByMap(map);
        List<Long> tagIdList = list.stream().map(BlogTagDO::getTagId).collect(Collectors.toList());
        return tagIdList;
    }

    @Override
    public List<TagDO> getTagListByBlogId(Long blogId) {
        Map<String, Object> map = new HashMap<>();
        map.put("blog_id", blogId);
        List<BlogTagDO> list = blogTagMapper.selectByMap(map);
        List<Long> tagIdList = list.stream().map(BlogTagDO::getTagId).collect(Collectors.toList());
        System.out.println("-------" + tagIdList);
        List<TagDO> tagList = tagMapper.selectBatchIds(tagIdList);
        return tagList;
    }

    @Override
    public Boolean deleteBlogTag(Long blogId, Long tagId) {
        Map<String, Object> map = new HashMap<>();
        map.put("blog_id", blogId);
        map.put("tag_id", tagId);
        int ret = blogTagMapper.deleteByMap(map);
        return ret != 0;
    }

    @Override
    public Boolean insertBlogTag(Long blogId, Long tagId) {
        List<Long> tagIdList = getTagIdListByBlogId(blogId);
        if (!tagIdList.contains(tagId)) {
            blogTagMapper.insert(new BlogTagDO(blogId, tagId));
            return true;
        }
        return false;
    }

}
