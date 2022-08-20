package top.silence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.Result;
import top.silence.service.VisitorService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @PutMapping("/visitor/pv/{ip}/{city}")
    public Result updatePvByIp(@PathVariable("ip") String ip, @PathVariable("city") String city) {
        // 根据ip获得city是在前端的接口完成的，所以这里一并带上city
        visitorService.updatePvByIp(ip, city);
        return Result.ok("成功更新pv");
    }

    @GetMapping("/visitor/pvuv")
    public Result getPvAndUv() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("pv", visitorService.getPv());
        map.put("uv", visitorService.getUv());
        return Result.ok("成功获得pv和uv", map);
    }
}
