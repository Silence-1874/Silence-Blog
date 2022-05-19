package top.silence.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.silence.dto.VisitorDO;
import top.silence.mapper.VisitorMapper;
import top.silence.service.VisitorService;

import java.util.Date;
import java.util.List;

/**
 * @author Silence
 * @date 2022/5/19 15:52
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, VisitorDO> implements VisitorService {

    @Autowired
    VisitorMapper visitorMapper;

    @Override
    public void updatePV(String ip, String city) {
        QueryWrapper<VisitorDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ip", ip);
        VisitorDO visitorDO = visitorMapper.selectOne(queryWrapper);
        Date updateTime = new Date();
        // 把一个ip看作一个用户，如果找不到，则新建
        if (visitorDO == null) {
            visitorDO = new VisitorDO(null, 1, ip, city, updateTime, updateTime);
            visitorMapper.insert(visitorDO);
            return;
        }
        visitorDO.setLastTime(updateTime);
        visitorDO.setPv(visitorDO.getPv() + 1);
        visitorMapper.updateById(visitorDO);
    }

    @Override
    public Integer getPV() {
        List<VisitorDO> visitors = visitorMapper.selectList(null);
        int pv = 0;
        for (VisitorDO visitor : visitors) {
            pv += visitor.getPv();
        }
        return pv;
    }

    @Override
    public Integer getUV() {
        return Math.toIntExact(visitorMapper.selectCount(null));
    }
}
