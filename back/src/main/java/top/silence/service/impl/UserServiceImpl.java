package top.silence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.UserDO;
import top.silence.mapper.UserMapper;
import top.silence.service.UserService;

/**
 * @author Silence
 * @date 2022/5/15 10:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getPasswordByUsername(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO user = userMapper.selectOne(queryWrapper);
        return user == null ? null : user.getPassword();
    }
}
