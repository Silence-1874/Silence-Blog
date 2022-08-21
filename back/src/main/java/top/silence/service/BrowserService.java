package top.silence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.BrowserDO;

/**
 * @author Silence
 * @date 2022/8/20 20:47
 */
public interface BrowserService extends IService<BrowserDO> {

    /**
     * 添加指定页面的浏览记录
     * @author Silence
     * @date 2022/8/21 10:27
     */
    void add(String page);

}
