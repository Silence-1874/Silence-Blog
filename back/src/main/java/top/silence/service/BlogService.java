package top.silence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.dto.BlogWriteDTO;
import top.silence.entity.BlogDO;

/**
 * @author Silence
 * @date 2022/5/12 8:53
 */
public interface BlogService extends IService<BlogDO> {

    /**
     * 根据分页信息获得分页数据
     * @author Silence
     * @date 2022/5/12 9:22
     */
    Page<BlogDO> listBlog(Integer pageNum, Integer pageSize);

    /**
     * 根据分类Id获得此分类下的博客分页数据
     * @author Silence
     * @date 2022/5/14 8:33
     */
    Page<BlogDO> listPageByCategoryId(Long categoryId, Integer pageNum, Integer pageSize);

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

}
