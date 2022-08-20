package top.silence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.silence.entity.VisitorDO;

/**
 * @author Silence
 * @date 2022/5/19 15:50
 */
public interface VisitorService extends IService<VisitorDO> {
    /**
     * 根据ip更新pv，传入city为了新建visitor
     * @author Silence
     * @date 2022/5/19 17:15
     */
    void updatePvByIp(String ip, String city);

    /**
     * 获得pv：所有访客的uv总和
     * @author Silence
     * @date 2022/5/19 17:37
     */
    Integer getPv();

    /**
     * 获得uv：不同ip的访客数
     * @author Silence
     * @date 2022/5/19 17:36
     */
    Integer getUv();
}
