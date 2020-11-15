package com.jiabb.springbootcachetest.cacheservice;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

/**
 * @author jiabinbin
 * @date 2020/11/15 11:40 下午
 * @classname LocalCacheProviderImpl
 * @description 本地缓存提供者服务（Guavu Cache）
 *  注意事项
 *      Guava Cache初始化容器时，支持缓存过期策略，类似FIFO、LRU和LFU等算法。
 *      expireAfterWrite：最后一次写入后的一段时间移出。
 *      expireAfterAccess：最后一次访问后的一段时间移出。
 *      Guava Cache对缓存过期时间的设置实在不够友好。常见的应用场景，比如，有些几乎不变的基础数据缓存1天，有些热点数据缓存2小时，有些会话数据缓存5分钟等等。
 *      通常我们认为设置缓存的时候带上缓存的过期时间是非常容易的，而且只要一个缓存容器实例即可，比如.NET下的ObjectCache、System.Runtime.Cache等等。
 *      但是Guava Cache不是这个实现思路，如果缓存的过期时间不同，Guava的CacheBuilder要初始化多份Cache实例。
 *      好在我在实现的时候注意到了这个问题，并且提供了解决方案，可以看到getCacheContainer这个函数，根据过期时长做缓存实例判断，就算不同过期时间的多实例缓存也是完全没有问题的。
 */
public class LocalCacheProviderImpl implements CacheProviderService {

    private final static long CACHE_MAXIMUM_SIZE = 20;
    private final static long CACHE_MINUTE = 10;

    /**
     * 初始化一个线程安全的map 缓存
     */
    private static Map<String, Cache<String,Object>> cacheMap = Maps.newConcurrentMap();

    static {
        Cache<String, Object> cacheContainer = CacheBuilder.newBuilder()
                .maximumSize(CACHE_MAXIMUM_SIZE)
                .expireAfterWrite(CACHE_MINUTE, TimeUnit.MINUTES)//最后一次写入的一段时间移除
//                .expireAfterAccess(CACHE_MINUTE, TimeUnit.MILLISECONDS) //最后一次访问的一段时间移除
                .recordStats()//开启统计功能
                .build();

        cacheMap.put(String.valueOf(CACHE_MINUTE),cacheContainer);
    }

    /**
     * 查询缓存
     *
     * @param key 缓存键 不可为空
     **/
    @Override
    public <T> T get(String key) {
        T obj = get(key, null, null, CACHE_MINUTE);
        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     **/
    @Override
    public <T> T get(String key, Function<String, T> function) {
        T obj = get(key, function, key, CACHE_MINUTE);
        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key      缓存键 不可为空
     * @param function 如没有缓存，调用该callable函数返回对象 可为空
     * @param funcParm function函数的调用参数
     **/
    @Override
    public <T, M> T get(String key, Function<M, T> function, M funcParm) {
        T obj = get(key, function, funcParm, CACHE_MINUTE);
        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    @Override
    public <T> T get(String key, Function<String, T> function, Long expireTime) {
        T obj = get(key, function, key, expireTime);
        return obj;
    }

    /**
     * 查询缓存
     *
     * @param key        缓存键 不可为空
     * @param function   如没有缓存，调用该callable函数返回对象 可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    @Override
    public <T, M> T get(String key, Function<M, T> function, M funcParm, Long expireTime) {
        T obj = null;
        //对key进行判断
        if (StringUtils.isEmpty(key) == true) {
            return obj;
        }
        //过期时间的一个校验赋值
        expireTime = getExpireTime(expireTime);

        Cache<String, Object> cacheContainer = getCacheContainer(expireTime);
        try {
            if (function == null) {
                obj = (T) cacheContainer.getIfPresent(key);
            } else {
                obj = (T) cacheContainer.get(key, () -> {
                    T retObj = function.apply(funcParm);
                    return retObj;
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;

    }

    /**
     * 设置缓存键值  直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值
     *
     * @param key 缓存键 不可为空
     * @param obj 缓存值 不可为空
     **/
    @Override
    public <T> void set(String key, T obj) {
        set(key, obj, CACHE_MINUTE);
    }

    /**
     * 设置缓存键值  直接向缓存中插入值，这会直接覆盖掉给定键之前映射的值
     *
     * @param key        缓存键 不可为空
     * @param obj        缓存值 不可为空
     * @param expireTime 过期时间（单位：毫秒） 可为空
     **/
    @Override
    public <T> void set(String key, T obj, Long expireTime) {
        if (StringUtils.isEmpty(key) == true) {
            return;
        }

        if (obj == null) {
            return;
        }

        expireTime = getExpireTime(expireTime);

        Cache<String, Object> cacheContainer = getCacheContainer(expireTime);

        cacheContainer.put(key, obj);
    }

    /**
     * 移除缓存
     *
     * @param key 缓存键 不可为空
     **/
    @Override
    public void remove(String key) {
        if (StringUtils.isEmpty(key) == true) {
            return;
        }

        long expireTime = getExpireTime(CACHE_MINUTE);

        Cache<String, Object> cacheContainer = getCacheContainer(expireTime);

        cacheContainer.invalidate(key);
    }

    /**
     * 是否存在缓存
     *
     * @param key 缓存键 不可为空
     **/
    @Override
    public boolean contains(String key) {
        boolean exists = false;
        if (StringUtils.isEmpty(key) == true) {
            return exists;
        }

        Object obj = get(key);

        if (obj != null) {
            exists = true;
        }

        return exists;
    }


    private static Lock lock = new ReentrantLock();

    /**
     * 以过期时间作为key的缓存map中 如果存在则直接返回 如果不存在 则加锁进行初始化创建
     * @param expireTime
     * @return
     */
    private Cache<String, Object> getCacheContainer(Long expireTime) {

        Cache<String, Object> cacheContainer = null;
        if (expireTime == null) {
            return cacheContainer;
        }

        String mapKey = String.valueOf(expireTime);

        if (cacheMap.containsKey(mapKey) == true) {
            cacheContainer = cacheMap.get(mapKey);
            return cacheContainer;
        }

        try {
            lock.lock();
            cacheContainer = CacheBuilder.newBuilder()
                    .maximumSize(CACHE_MAXIMUM_SIZE)
                    .expireAfterWrite(expireTime, TimeUnit.MILLISECONDS)//最后一次写入后的一段时间移出
                    //.expireAfterAccess(AppConst.CACHE_MINUTE, TimeUnit.MILLISECONDS) //最后一次访问后的一段时间移出
                    .recordStats()//开启统计功能
                    .build();

            cacheMap.put(mapKey, cacheContainer);

        } finally {
            lock.unlock();
        }

        return cacheContainer;
    }

    /**
     * 获取过期时间 单位：毫秒
     *
     * @param expireTime 传人的过期时间 单位毫秒 如小于1分钟，默认为10分钟
     **/
    private Long getExpireTime(Long expireTime) {
        Long result = expireTime;
        if (expireTime == null || expireTime < CACHE_MINUTE / 10) {
            result = CACHE_MINUTE;
        }
        return result;
    }
}
