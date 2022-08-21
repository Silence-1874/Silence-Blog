package top.silence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.constant.RedisConstants;
import top.silence.dto.Result;
import top.silence.entity.AboutDO;
import top.silence.service.AboutService;

@RestController
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/about")
    public Result get() {
        String redisKey1 = RedisConstants.ABOUT_TITLE;
        String redisKey2 = RedisConstants.ABOUT_CONTENT;
        if (redisKey1 == null && redisKey2 == null) {
            String title = redisTemplate.opsForValue().get(redisKey1);
            String content = redisTemplate.opsForValue().get(redisKey2);
            return Result.ok("'关于我'请求成功", new AboutDO("1", title, content));
        }
        AboutDO about = aboutService.getById(1);
        return Result.ok("'关于我'请求成功", about);
    }

}
