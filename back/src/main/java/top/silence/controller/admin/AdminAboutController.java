package top.silence.controller.admin;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.constant.RedisConstants;
import top.silence.dto.Result;
import top.silence.entity.AboutDO;
import top.silence.service.AboutService;

@SaCheckLogin
@RestController
@RequestMapping("/admin")
public class AdminAboutController {

    @Autowired
    private AboutService aboutService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @SaCheckRole("admin")
    @PutMapping("/about")
    public Result update(@RequestBody AboutDO about) {
        String redisKey1 = RedisConstants.ABOUT_TITLE;
        String redisKey2 = RedisConstants.ABOUT_CONTENT;
        redisTemplate.delete(redisKey1);
        redisTemplate.delete(redisKey2);
        aboutService.updateById(about);
        redisTemplate.opsForValue().set(redisKey1, about.getTitle());
        redisTemplate.opsForValue().set(redisKey2, about.getContent());
        return Result.ok("'关于我'修改成功", about);
    }

}
