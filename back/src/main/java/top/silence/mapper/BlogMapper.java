package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.silence.entity.BlogDO;

import java.util.List;

/**
 * @author Silence
 * @date 2022/5/12 8:55
 */
@Mapper
public interface BlogMapper extends BaseMapper<BlogDO> {

    /**
     * 手动分页查询当前标签下的所有文章
     * @author Silence
     * @date 2022/5/17 19:39
     */
    @Select("SELECT * FROM blog RIGHT JOIN blog_tag ON blog.id = blog_tag.blog_id RIGHT JOIN tag ON tag.id = blog_tag.tag_id WHERE tag.id = #{tagId} ORDER BY is_top DESC, create_time DESC, update_time DESC")
    List<BlogDO> pageByTag(Page<BlogDO> page, @Param("tagId") Long tagId);

    /**
     * 统计当前标签下的文章总数
     * @author Silence
     * @date 2022/5/17 19:58
     */
    @Select("SELECT COUNT(*) FROM blog RIGHT JOIN blog_tag ON blog.id = blog_tag.blog_id RIGHT JOIN tag ON tag.id = blog_tag.tag_id WHERE tag.id = #{tagId}")
    Long countBlogInTag(@Param("tagId") Long tagId);

}
