package top.silence.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.TagDO;
import top.silence.mapper.TagMapper;
import top.silence.service.TagService;

/**
 * @author Silence
 * @date 2022/5/12 19:56
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Page<TagDO> page(Integer pageNum, Integer pageSize) {
        Page<TagDO> page = new Page<>(pageNum, pageSize);
        return tagMapper.selectPage(page, null);
    }

}
