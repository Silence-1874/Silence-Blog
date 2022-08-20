package top.silence.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.CategoryDO;

/**
 * @author Silence
 * @date 2022/5/12 15:12
 */
public interface CategoryService extends IService<CategoryDO> {

    /**
     * 根据分页信息获得分页数据
     * @author Silence
     * @date 2022/5/12 15:17
     */
    Page<CategoryDO> page(Integer pageNum, Integer pageSize);

}
