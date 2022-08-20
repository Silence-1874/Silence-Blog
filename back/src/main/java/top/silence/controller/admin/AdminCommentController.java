package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.silence.dto.Result;
import top.silence.entity.CommentDO;
import top.silence.service.CommentService;

import javax.websocket.server.PathParam;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public Result pageByQuery(@PathParam("pageNum") Integer pageNum,
                              @PathParam("pageSize") Integer pageSize,
                              @PathParam("blogId") Long blogId) {
        Page<CommentDO> pages = commentService.pageByQuery(pageNum, pageSize, blogId);
        return Result.ok("成功获得评论分页信息", pages);
    }

    @SaCheckRole("admin")
    @DeleteMapping("/comment/{id}")
    public Result deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return Result.ok("成功删除评论及其下的回复");
    }

    @SaCheckRole("admin")
    @PutMapping("/comment")
    public Result update(@RequestBody CommentDO comment) {
        UpdateWrapper<CommentDO> updateWrapper = new UpdateWrapper<CommentDO>().eq("id", comment.getId());
        commentService.update(comment, updateWrapper);
        return Result.ok("成功修改评论");
    }

}
