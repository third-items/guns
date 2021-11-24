/*
 * Copyright [2020-2030] [https://www.stylefeng.cn]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Guns源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */
package cn.stylefeng.guns.modular.business.wx.miniapp.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.stylefeng.guns.modular.business.wx.miniapp.cache.MemoryWxLoginUserCache;
import cn.stylefeng.guns.modular.business.wx.miniapp.pojo.WxLoginUser;
import cn.stylefeng.guns.modular.business.wx.miniapp.service.WxSessionManagerService;
import cn.stylefeng.guns.modular.business.wx.miniapp.service.impl.WxSessionManagerServiceImpl;
import cn.stylefeng.roses.kernel.auth.api.SessionManagerApi;
import cn.stylefeng.roses.kernel.auth.api.cookie.SessionCookieCreator;
import cn.stylefeng.roses.kernel.auth.api.expander.AuthConfigExpander;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordStoredEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.password.PasswordTransferEncryptApi;
import cn.stylefeng.roses.kernel.auth.api.pojo.login.LoginUser;
import cn.stylefeng.roses.kernel.auth.password.BcryptPasswordStoredEncrypt;
import cn.stylefeng.roses.kernel.auth.password.RsaPasswordTransferEncrypt;
import cn.stylefeng.roses.kernel.auth.session.DefaultSessionManager;
import cn.stylefeng.roses.kernel.auth.session.cache.logintoken.MemoryLoginTokenCache;
import cn.stylefeng.roses.kernel.auth.session.cache.loginuser.MemoryLoginUserCache;
import cn.stylefeng.roses.kernel.auth.session.cookie.DefaultSessionCookieCreator;
import cn.stylefeng.roses.kernel.cache.api.CacheOperatorApi;
import cn.stylefeng.roses.kernel.cache.api.constants.CacheConstants;
import cn.stylefeng.roses.kernel.jwt.JwtTokenOperator;
import cn.stylefeng.roses.kernel.jwt.api.JwtApi;
import cn.stylefeng.roses.kernel.jwt.api.pojo.config.JwtConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;


/**
 * 认证和鉴权模块的自动配置
 *
 * @author fengshuonan
 * @date 2020/11/30 22:16
 */
@Configuration
public class WxLoginConfiguration {

    /**
     * 登录用户的缓存，默认使用内存方式
     * <p>
     * 如需redis，可在项目创建一个名为 loginUserCache 的bean替代即可
     *
     * @author fengshuonan
     * @date 2021/1/31 21:04
     */
    @Bean
    @ConditionalOnMissingBean(name = "wxLoginUserCache")
    public MemoryWxLoginUserCache wxLoginUserCache() {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        TimedCache<String, WxLoginUser> loginUsers = CacheUtil.newTimedCache(1000L * sessionExpiredSeconds);
        return new MemoryWxLoginUserCache(loginUsers);
    }


    /**
     * 默认的session缓存为内存缓存，方便启动
     * <p>
     * 如需替换请在项目中增加一个SessionManagerApi Bean即可
     *
     * @author fengshuonan
     * @date 2020/11/30 22:17
     */
    @Bean
    @ConditionalOnMissingBean(WxSessionManagerService.class)
    public WxSessionManagerService WxSessionManagerService(CacheOperatorApi<WxLoginUser> loginUserCache) {
        Long sessionExpiredSeconds = AuthConfigExpander.getSessionExpiredSeconds();
        return new WxSessionManagerServiceImpl(loginUserCache, sessionExpiredSeconds);
    }

}
