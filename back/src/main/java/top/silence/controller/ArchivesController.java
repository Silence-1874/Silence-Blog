package top.silence.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.silence.dto.ArchiveDTO;
import top.silence.dto.Result;
import top.silence.service.BlogService;

import java.util.List;
import java.util.Map;

@RestController
public class ArchivesController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public Result archive() {
        Map<String, List<ArchiveDTO>> archive = blogService.archive();
        return Result.ok("成功获得博客归档信息", archive);
    }

}
