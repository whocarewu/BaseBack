package com.cn.base.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类，封装常用 Redis 操作，基于 Spring Data Redis 的 RedisTemplate 实现。
 * <p>
 * 支持字符串、哈希、列表、集合、有序集合的基本操作。
 * 所有操作均线程安全，直接注入使用。
 * </p>
 */
@Component
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造函数，注入 RedisTemplate 实例。
     *
     * @param redisTemplate RedisTemplate实例
     */
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ====================== Key 操作 ======================

    /**
     * 设置 key 的过期时间，单位秒。
     *
     * @param key            Redis键
     * @param timeoutSeconds 过期时间，秒
     * @return 是否设置成功
     */
    public boolean expire(String key, long timeoutSeconds) {
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("timeoutSeconds must be greater than zero");
        }
        return Boolean.TRUE.equals(redisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS));
    }

    /**
     * 获取 key 的剩余过期时间，单位秒。
     *
     * @param key Redis键
     * @return 剩余过期时间（秒），-1 表示永不过期，-2 表示 key 不存在
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在。
     *
     * @param key Redis键
     * @return true 存在，false 不存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除一个或多个 key。
     *
     * @param keys 要删除的key数组
     */
    public void delete(String... keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        if (keys.length == 1) {
            redisTemplate.delete(keys[0]);
        } else {
            List<String> keyList = new ArrayList<>(keys.length);
            for (String key : keys) {
                keyList.add(key);
            }
            redisTemplate.delete(keyList);
        }
    }

    // ====================== String 操作 ======================

    /**
     * 获取指定 key 的值。
     *
     * @param key Redis键
     * @return key对应的值，如果不存在返回null
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置 key 的值，无过期时间。
     *
     * @param key   Redis键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置 key 的值，并设置过期时间。
     *
     * @param key            Redis键
     * @param value          值
     * @param timeoutSeconds 过期时间，秒
     */
    public void set(String key, Object value, long timeoutSeconds) {
        if (timeoutSeconds <= 0) {
            throw new IllegalArgumentException("timeoutSeconds must be greater than zero");
        }
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    /**
     * 将 key 的值递增 delta（正数）。
     *
     * @param key   Redis键
     * @param delta 递增幅度，必须大于0
     * @return 增加后的值
     */
    public long increment(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("delta must be greater than zero");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 将 key 的值递减 delta（正数）。
     *
     * @param key   Redis键
     * @param delta 递减幅度，必须大于0
     * @return 减少后的值
     */
    public long decrement(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("delta must be greater than zero");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ====================== Hash 操作 ======================

    /**
     * 获取哈希表 key 中指定字段 field 的值。
     *
     * @param key   Redis键
     * @param field 哈希字段
     * @return 字段对应的值，如果不存在返回null
     */
    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 获取哈希表 key 中的所有字段和值。
     *
     * @param key Redis键
     * @return 字段-值Map，如果不存在返回空Map
     */
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 向哈希表 key 中放入字段 field 和对应的值。
     *
     * @param key   Redis键
     * @param field 哈希字段
     * @param value 值
     */
    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向哈希表 key 中批量放入字段和值。
     *
     * @param key Redis键
     * @param map 字段-值Map
     */
    public void hSetAll(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 删除哈希表 key 中的一个或多个指定字段。
     *
     * @param key    Redis键
     * @param fields 哈希字段数组
     */
    public void hDelete(String key, Object... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    /**
     * 判断哈希表 key 中是否存在字段 field。
     *
     * @param key   Redis键
     * @param field 哈希字段
     * @return true 存在，false 不存在
     */
    public boolean hHasKey(String key, String field) {
        return Boolean.TRUE.equals(redisTemplate.opsForHash().hasKey(key, field));
    }

    /**
     * 为哈希表 key 中的字段 field 的值加上增量 delta。
     *
     * @param key   Redis键
     * @param field 哈希字段
     * @param delta 增量（可正可负）
     * @return 加上增量后的新值
     */
    public long hIncrement(String key, String field, long delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    // ====================== List 操作 ======================

    /**
     * 获取列表 key 中指定范围的元素。
     *
     * @param key   Redis键
     * @param start 开始索引（0-based）
     * @param end   结束索引（-1表示最后一个元素）
     * @return 元素列表，如果key不存在返回空列表
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取列表 key 的长度。
     *
     * @param key Redis键
     * @return 列表长度，如果key不存在返回0
     */
    public long lSize(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0 : size;
    }

    /**
     * 将元素 value 插入到列表 key 的尾部。
     *
     * @param key   Redis键
     * @param value 元素值
     */
    public void lRightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 移除并返回列表 key 的尾部元素。
     *
     * @param key Redis键
     * @return 尾部元素，如果列表为空返回null
     */
    public Object lRightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    // ====================== Set 操作 ======================

    /**
     * 获取集合 key 的所有成员。
     *
     * @param key Redis键
     * @return 成员集合，如果key不存在返回空集合
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 向集合 key 中添加一个或多个成员。
     *
     * @param key    Redis键
     * @param values 一个或多个成员
     * @return 添加成功的成员数量是否大于0
     */
    public boolean sAdd(String key, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        return count != null && count > 0;
    }

    /**
     * 判断成员 value 是否是集合 key 的成员。
     *
     * @param key   Redis键
     * @param value 成员值
     * @return true 是成员，false 不是成员
     */
    public boolean sIsMember(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 移除集合 key 中的一个或多个成员。
     *
     * @param key    Redis键
     * @param values 一个或多个成员
     * @return 移除的成员数量
     */
    public long sRemove(String key, Object... values) {
        Long count = redisTemplate.opsForSet().remove(key, values);
        return count == null ? 0 : count;
    }

    // ====================== Sorted Set 操作 ======================

    /**
     * 向有序集合 key 添加成员 value ，并设置分数 score。
     *
     * @param key   Redis键
     * @param value 成员值
     * @param score 分数
     * @return 添加是否成功
     */
    public Boolean zAdd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取有序集合 key 中指定区间内的成员。
     *
     * @param key   Redis键
     * @param start 开始索引
     * @param end   结束索引
     * @return 成员集合（按分数从低到高排序）
     */
    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 移除有序集合 key 中的一个或多个成员。
     *
     * @param key    Redis键
     * @param values 一个或多个成员
     * @return 移除的成员数量
     */
    public Long zRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    /**
     * 获取有序集合 key 中成员 value 的分数。
     *
     * @param key   Redis键
     * @param value 成员值
     * @return 分数，如果成员不存在返回null
     */
    public Double zScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }
}
