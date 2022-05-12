package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.silence.entity.BlogDO;

/**
 * @author Silence
 * @date 2022/5/12 8:55
 */
@Mapper
public interface BlogMapper extends BaseMapper<BlogDO> {
}
