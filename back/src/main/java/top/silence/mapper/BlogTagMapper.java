package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.silence.entity.BlogTagDO;

/**
 * @author Silence
 * @date 2022/5/13 9:30
 */
@Mapper
public interface BlogTagMapper extends BaseMapper<BlogTagDO> {
}
