package top.silence.handler;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.silence.dto.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常拦截（拦截项目中的所有异常）
    @ResponseBody
    @ExceptionHandler
    public Result handlerException(Exception e, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        // 打印堆栈，以供调试
        System.out.println("全局异常---------------");
        e.printStackTrace();

        if (e instanceof NotLoginException) {
            // 未登录异常
            return Result.fail("请先登录！");
        } else if (e instanceof NotRoleException) {
            // 权限异常（直接与角色绑定了）
            return Result.fail("无此权限！");
        }

        // 返回给前端
        return Result.fail(e.getMessage());
    }

}