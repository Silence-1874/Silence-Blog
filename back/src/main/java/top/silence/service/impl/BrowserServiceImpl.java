package top.silence.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.silence.entity.BrowserDO;
import top.silence.mapper.BrowserMapper;
import top.silence.service.BrowserService;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author Silence
 * @date 2022/8/20 20:53
 */
@Service
public class BrowserServiceImpl extends ServiceImpl<BrowserMapper, BrowserDO> implements BrowserService {

    @Override
    public void add(String page) {
        // 不记录博主的浏览信息
        if (StpUtil.getRoleList() != null && StpUtil.getRoleList().size() > 0 &&
                "admin".equals(StpUtil.getRoleList().get(0))) {
            return;
        }

        // 获得 request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 获得用户真实ip
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("getIpAddress exception:", e);
                }
                ip = inet.getHostAddress();
            }
        }

        // 添加浏览记录
        StringBuffer url = request.getRequestURL();
        String queryString = request.getQueryString();
        // 拼接请求参数
        if (queryString != null && queryString.length() != 0) {
            url.append(queryString);
        }
        Date time = new Date();
        save(new BrowserDO(null, ip, page, url.toString(), time));
    }

}
