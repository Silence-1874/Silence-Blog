package top.silence.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.CommentDO;
import top.silence.service.CommentService;

import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comment/{blogId}/{pageNum}/{pageSize}")
    public Result listComment(@PathVariable("blogId") Long blogId, @PathVariable("pageNum") Integer pageNum, @PathVariable("pageSize") Integer pageSize) {
        Map<String, Object> map = commentService.listPage(blogId, pageNum, pageSize);
        return Result.ok("成功获得评论列表", map);
    }

    @PostMapping("/comment")
    public Result submit(@RequestBody CommentDO comment) {
        // 评论内容合法性校验
        if (StrUtil.isBlank(comment.getContent()) || comment.getContent().length() > 250 ||
                comment.getBlogId() == null || comment.getParentCommentId() == null) {
            return Result.fail("参数有误");
        }
        // QQ号合法性校验
        if (!comment.getIsAdmin()) {
            String url = "https://api.usuuu.com/qq/" + comment.getNickname();
            Integer code = (Integer) JSONUtil.parseObj(HttpRequest.get(url).execute().body()).get("code");
            if (Integer.valueOf(500).equals(code)) {
                return Result.fail("请输入正确的QQ号！");
            }
        }
        commentService.addComment(comment);
        return Result.ok("评论成功", comment.getQq());
    }

}
