package top.silence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.CommentDO;

/**
 * @author Silence
 * @date 2022/5/18 10:16
 */
public interface CommentService extends IService<CommentDO> {

    /**
     * 根据查询信息获得分页数据
     * @author Silence
     * @date 2022/5/18 11:00
     */
    Page<CommentDO> searchComment(Integer pageNum, Integer pageSize, Long blogId);

    /**
     * 根据Id删除评论以及其下的所有回复
     * @author Silence
     * @date 2022/5/18 14:36
     */
    void deleteComment(Long id);

}
