package cn.stylefeng.guns.config;

import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.session.cache.logintoken.RedisLoginTokenCache;
import cn.stylefeng.roses.kernel.auth.session.cache.loginuser.RedisLoginUserCache;
import cn.stylefeng.roses.kernel.auth.session.timer.ClearInvalidLoginUserCacheTimer;
import cn.stylefeng.roses.kernel.cache.redis.util.CreateRedisTemplateUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;


/**
 * 认证和鉴权模块的自动配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:16
 */
@Configuration
public class RedisBaseAuthConfig {

    /**
     * 登录用户缓存的redis操作类
     *
     * @author fengshuonan
     * @date 2021/5/18 16:18
     */
    @Bean
    public RedisTemplate<String, LoginUser> loginUserRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return CreateRedisTemplateUtil.createObject(redisConnectionFactory);
    }

    /**
     * token缓存的redis操作类
     *
     * @author fengshuonan
     * @date 2021/5/18 16:18
     */
    @Bean
    public RedisTemplate<String, Set<String>> loginTokenCache(RedisConnectionFactory redisConnectionFactory) {
        return CreateRedisTemplateUtil.createObject(redisConnectionFactory);
    }

    /**
     * 登录用户的缓存，使用redis方式
     *
     * @author fengshuonan
     * @date 2021/1/31 21:04
     */
    @Bean(name = "loginUserCache")
    public RedisLoginUserCache loginUserCache(RedisTemplate<String, LoginUser> loginUserRedisTemplate) {
        return new RedisLoginUserCache(loginUserRedisTemplate);
    }

    /**
     * 登录用户token的缓存，使用redis方式
     *
     * @author fengshuonan
     * @date 2021/1/31 21:04
     */
    @Bean(name = "allPlaceLoginTokenCache")
    public RedisLoginTokenCache allPlaceLoginTokenCache(RedisTemplate<String, Set<String>> loginTokenCache) {
        return new RedisLoginTokenCache(loginTokenCache);
    }

    /**
     * 清空无用登录用户缓存的定时任务
     *
     * @author fengshuonan
     * @date 2021/3/30 11:32
     */
    @Bean
    public ClearInvalidLoginUserCacheTimer clearInvalidLoginUserCacheTimer(RedisTemplate<String, LoginUser> loginUserRedisTemplate, RedisTemplate<String, Set<String>> loginTokenCache) {
        return new ClearInvalidLoginUserCacheTimer(loginUserCache(loginUserRedisTemplate), allPlaceLoginTokenCache(loginTokenCache));
    }

}
