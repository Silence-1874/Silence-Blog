package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.silence.entity.CommentDO;

/**
 * @author Silence
 * @date 2022/5/18 10:15
 */
@Mapper
public interface CommentMapper extends BaseMapper<CommentDO> {
}
