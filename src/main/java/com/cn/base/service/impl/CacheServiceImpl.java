package com.cn.base.service.impl;

import com.cn.base.service.CacheService;

import com.cn.base.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final RedisUtil redisUtil;

    @Override
    public boolean setRedis() {
        redisUtil.set("test", "test");
        return false;
    }
}
