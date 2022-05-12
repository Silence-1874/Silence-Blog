package top.silence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.silence.entity.CategoryDO;

/**
 * @author Silence
 * @date 2022/5/12 15:04
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryDO> {
}
