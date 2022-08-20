package top.silence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.UserDO;

/**
 * @author Silence
 * @date 2022/5/15 10:23
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名获取密码
     * @author Silence
     * @date 2022/5/15 10:40
     */
    String getPasswordByUsername(String username);

}
