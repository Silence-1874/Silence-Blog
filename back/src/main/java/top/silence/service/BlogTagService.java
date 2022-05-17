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
     * 建立博客Id和标签Id的一对多映射(保证标签Id不重复)
     * @author Silence
     * @date 2022/5/13 9:37
     */
    void mapBlogTag(Long blogId, List<TagDO> tagList);

    /**
     * 根据博客Id返回其下的标签Id列表
     * @author Silence
     * @date 2022/5/13 16:05
     */
    List<Long> getTagIdListByBlogId(Long blogId);

    /**
     * 根据博客Id返回其下的标签列表
     * @author Silence
     * @date 2022/5/17 11:33
     */
    List<TagDO> getTagListByBlogId(Long blogId);

    /**
     * 删除博客Id和标签Id的某条对应关系
     * @author Silence
     * @date 2022/5/13 16:55
     */
    Boolean deleteBlogTag(Long blogId, Long tagId);

    /**
     * 新建一条博客Id和标签Id的对应关系
     * @author Silence
     * @date 2022/5/13 20:03
     */
    Boolean insertBlogTag(Long blogId, Long tagId);
}
