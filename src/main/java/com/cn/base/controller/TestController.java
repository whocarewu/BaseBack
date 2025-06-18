package com.cn.base.controller;


import com.cn.base.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {
    private final CacheService cacheService;

    @GetMapping("/hello")
    public String test() {
        return "嗨喽";
    }

    @GetMapping("/setRedis")
    public String setRedis() {
        boolean redis = cacheService.setRedis();
        return "嗨喽";
    }
}
