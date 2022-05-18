package top.silence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.CommentDO;
import top.silence.mapper.CommentMapper;
import top.silence.service.CommentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silence
 * @date 2022/5/18 10:17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, CommentDO> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Page<CommentDO> searchComment(Integer pageNum, Integer pageSize, Long blogId) {
        Page<CommentDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CommentDO> queryWrapper= new QueryWrapper<>();
        if (blogId != null) {
            queryWrapper.eq("blog_id", blogId);
        }
        queryWrapper.orderByDesc("create_time");
        return commentMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deleteComment(Long id) {
        CommentDO comment = commentMapper.selectById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("parent_comment_id", comment.getId());
        List<CommentDO> replys = commentMapper.selectByMap(map);
        // 此评论下没有回复，则返回
        if (replys == null) {
            commentMapper.deleteById(id);
            return;
        }
        // 递归删除评论的回复
        for (CommentDO reply : replys) {
            deleteComment(reply.getId());
        }
        commentMapper.deleteById(id);
    }

}
