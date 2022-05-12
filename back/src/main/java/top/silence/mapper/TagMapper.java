package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.silence.entity.TagDO;

/**
 * @author Silence
 * @date 2022/5/12 19:54
 */
@Mapper
public interface TagMapper extends BaseMapper<TagDO> {
}
