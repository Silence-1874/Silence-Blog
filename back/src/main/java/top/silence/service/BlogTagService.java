package top.silence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.BlogTagDO;
import top.silence.entity.TagDO;

import java.util.List;

/**
 * @author Silence
 * @date 2022/5/13 9:31
 */
public interface BlogTagService extends IService<BlogTagDO> {

    /**
     * 建立博客Id和标签Id的一对多映射
     * @author Silence
     * @date 2022/5/13 9:37
     */
    void mapBlogTag(Long blogId, List<TagDO> tagList);

}
