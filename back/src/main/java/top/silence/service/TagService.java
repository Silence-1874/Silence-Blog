package top.silence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.TagDO;

/**
 * @author Silence
 * @date 2022/5/12 19:55
 */
public interface TagService extends IService<TagDO> {

    /**
     * 根据分页信息获得分页数据
     * @author Silence
     * @date 2022/5/12 20:00
     */
    Page<TagDO> page(Integer pageNum, Integer pageSize);

}
