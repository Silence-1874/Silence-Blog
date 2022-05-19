package top.silence.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.dto.CommentDTO;
import top.silence.entity.CommentDO;
import top.silence.mapper.CommentMapper;
import top.silence.service.CommentService;

import java.util.*;

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
        if (replys == null || replys.size() == 0) {
            commentMapper.deleteById(id);
            return;
        }
        // 递归删除评论的回复
        for (CommentDO reply : replys) {
            deleteComment(reply.getId());
        }
        commentMapper.deleteById(id);
    }

    @Override
    public Map listPage(Long blogId, Integer pageNum, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Page<CommentDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<CommentDO> queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        queryWrapper.eq("parent_comment_id", -1);
        queryWrapper.orderByDesc("create_time");
        Page<CommentDO> pageList = commentMapper.selectPage(page, queryWrapper);
        List<CommentDTO> comments = commentDO2DTO(pageList.getRecords());
        map.put("allComment",pageList.getTotal());
        map.put("totalPage", pageList.getPages());
        // 查询某个根评论下的所有回复，包括非直接回复，将他们全都挂在根评论的ReplyComments下
        for (CommentDTO comment: comments) {
            List<CommentDTO> reply = getAllReplys(comment);
            comment.setReplyComments(reply);
        }
        map.put("comments",comments);
        return map;
    }

    @Override
    public List<CommentDTO> getAllReplys(CommentDTO root) {
        List<CommentDTO> ret = new ArrayList<>();
        dfs(ret, root);
        // 回复按时间升序
        int n = ret.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Date d1 = ret.get(j).getCreateTime();
                Date d2 = ret.get(j + 1).getCreateTime();
                if (d1.after(d2)) {
                    ret.get(j).setCreateTime(d2);
                    ret.get(j + 1).setCreateTime(d1);
                }
            }
        }
        return ret;
    }

    @Override
    public void addComment(CommentDO comment) {
        // 生成评论时间
        comment.setCreateTime(new Date());

        // 不是博主的评论，根据QQ号生成头像和名称
        if (!comment.getIsAdmin()) {
            String url = "https://api.usuuu.com/qq/" + comment.getNickname();
            JSONObject data = (JSONObject) JSONUtil.parseObj(HttpRequest.get(url).execute().body()).get("data");
            comment.setNickname((String) data.get("name"));
            comment.setAvatar((String) data.get("avatar"));
            comment.setQq((String) data.get("qq"));
        }
        commentMapper.insert(comment);
    }


    private void dfs(List<CommentDTO> ret, CommentDTO commentDTO) {
        List<CommentDTO> replyComments = commentDTO.getReplyComments();
        if (replyComments.size() == 0) {
            return;
        }
        for (CommentDTO reply : replyComments) {
            ret.add(reply);
            dfs(ret, reply);
        }
    }

    @Override
    public List<CommentDTO> commentDO2DTO(List<CommentDO> commentDOList) {
        List<CommentDTO> comments = new ArrayList<>();
        for (CommentDO comment : commentDOList) {
            Long id = comment.getId();
            CommentDTO commentDTO = new CommentDTO();
            BeanUtil.copyProperties(comment, commentDTO);
            // 根据父Id查找父昵称
            Long parentId = comment.getParentCommentId();
            if (!parentId.equals(-1L)) {
                commentDTO.setParentCommentNickname(commentMapper.selectById(parentId).getNickname());
            }
            // 查询回复
            Map<String, Object> map = new HashMap<>();
            map.put("parent_comment_id", id);
            List<CommentDO> replys = commentMapper.selectByMap(map);
            if (replys != null && replys.size() != 0) {
                // 递归将回复列表转换成DTO
                commentDTO.setReplyComments(commentDO2DTO(replys));
            }
            comments.add(commentDTO);
        }
        return comments;
    }

}
