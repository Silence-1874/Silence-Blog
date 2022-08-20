package top.silence.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.CategoryDO;
import top.silence.mapper.CategoryMapper;
import top.silence.service.CategoryService;

/**
 * @author Silence
 * @date 2022/5/12 15:13
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryDO> page(Integer pageNum, Integer pageSize) {
        Page<CategoryDO> page = new Page<>(pageNum, pageSize);
        return categoryMapper.selectPage(page, null);
    }

}
