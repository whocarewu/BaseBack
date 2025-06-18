package com.cn.base.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
public class TestController {


    @GetMapping("/hello")
    public String test() {
        return "嗨喽";
    }
}
