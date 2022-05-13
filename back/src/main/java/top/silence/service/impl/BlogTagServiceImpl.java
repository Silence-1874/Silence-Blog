package top.silence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.BlogTagDO;
import top.silence.entity.TagDO;
import top.silence.mapper.BlogTagMapper;
import top.silence.service.BlogTagService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Silence
 * @date 2022/5/13 9:34
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTagDO> implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

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

}
