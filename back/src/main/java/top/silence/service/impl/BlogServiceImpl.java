package top.silence.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.entity.BlogDO;
import top.silence.mapper.BlogMapper;
import top.silence.service.BlogService;

/**
 * @author Silence
 * @date 2022/5/12 8:55
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogDO> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public Page<BlogDO> listBlog(Integer pageNum, Integer pageSize) {
        // 设置分页参数
        Page<BlogDO> page = new Page<>(pageNum, pageSize);
        return blogMapper.selectPage(page, null);
    }

}
