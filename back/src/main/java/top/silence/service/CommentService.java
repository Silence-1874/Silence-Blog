package top.silence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.dto.CommentDTO;
import top.silence.entity.CommentDO;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据博客Id获得根评论
     * @author Silence
     * @date 2022/5/18 20:26
     */
    Map listPage(Long blogId, Integer pageNum, Integer pageSize);

    /**
     * 将CommentDO列表转换成CommentDTO列表
     * @author Silence
     * @date 2022/5/18 21:18
     */
    List<CommentDTO> commentDO2DTO(List<CommentDO> commentDOList);

    /**
     * 查询某个根评论下的所有回复，包括非直接回复
     * @author Silence
     * @date 2022/5/18 23:01
     */
    List<CommentDTO> getAllReplys(CommentDTO commentDTO);

    /**
     * 添加评论
     * @author Silence
     * @date 2022/5/19 8:12
     */
    void addComment(CommentDO comment);
}
