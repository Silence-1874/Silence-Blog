package top.silence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.dto.ArchiveDTO;
import top.silence.dto.BlogDTO;
import top.silence.dto.BlogWriteDTO;
import top.silence.entity.BlogDO;

import java.util.List;
import java.util.Map;

/**
 * @author Silence
 * @date 2022/5/12 8:53
 */
public interface BlogService extends IService<BlogDO> {

    /**
     * 根据查询信息获得分页数据
     * @author Silence
     * @date 2022/5/14 8:56
     */
    Page<BlogDO> searchBlog(Integer pageNum, Integer pageSize, Long categoryId, String title);

    /**
     * 根据参数获得博客数据
     * @author Silence
     * @date 2022/5/17 14:16
     */
    List<BlogDTO> listBlog(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 返回某个标签下的所有文章的分类数据
     * @author Silence
     * @date 2022/5/17 14:16
     */
    List<BlogDTO> listBlogInTag(Integer pageNum, Integer pageSize, Long tagId);

    /**
     * 写博文
     * @author Silence
     * @date 2022/5/12 22:29
     */
    Long saveBlog(BlogWriteDTO blogWriteDTO);

    /**
     * 修改博文
     * @author Silence
     * @date 2022/5/13 16:41
     */
    Long updateBlog(BlogWriteDTO blogWriteDTO, Long id);

    /**
     * 删除博文
     * @author Silence
     * @date 2022/5/14 7:49
     */
    void deleteBlog(Long id);

    /**
     * 按年月日归档博客
     * @author Silence
     * @date 2022/5/17 22:01
     */
    Map<String, List<ArchiveDTO>> archiveBlog();
}
