package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.silence.entity.UserDO;

/**
 * @author Silence
 * @date 2022/5/15 10:13
 */
@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
