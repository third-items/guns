package cn.stylefeng.guns.config;

import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.cache.redis.util.CreateRedisTemplateUtil;
import cn.stylefeng.roses.kernel.scanner.api.pojo.resource.ResourceDefinition;
import cn.stylefeng.roses.kernel.system.modular.resource.cache.RedisResourceCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 资源缓存自动配置
 *
 * @author fengshuonan
 * @date 2021/5/17 16:44
 */
@Configuration
public class RedisBaseResourceCacheConfig {

    /**
     * 资源缓存
     *
     * @author fengshuonan
     * @date 2021/5/17 16:44
     */
    @Bean(name = "resourceCache")
    public CacheOperatorApi<ResourceDefinition> resourceCache(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ResourceDefinition> redisTemplate = CreateRedisTemplateUtil.createObject(redisConnectionFactory);
        return new RedisResourceCache(redisTemplate);
    }

}
