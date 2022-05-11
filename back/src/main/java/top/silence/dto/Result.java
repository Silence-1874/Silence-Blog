package top.silence.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Silence
 * @date 2022/5/11 18:51
 * @ 封装统一返回结果
 */
@Data
@NoArgsConstructor
public class Result {
    private Boolean isSuccess;
    private String msg;
    private Object data;

    private Result(Boolean isSuccess, String msg) {
        this.isSuccess = isSuccess;
        this.msg = msg;
    }

    private Result(Boolean isSuccess, String msg, Object data) {
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok() {
        return new Result(true, "请求成功");
    }

    public static Result ok(Object data) {
        return new Result(true, "请求成功", data);
    }

    public static Result fail(String msg) {
        return new Result(false, msg, null);
    }
}
