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
package cn.stylefeng.guns.modular.business.wx.miniapp.context;

import cn.hutool.core.lang.Dict;

/**
 * 临时保存http请求的参数
 * <p>
 * 可以保存@RequestBody的可以保存parameter方式传参的
 *
 * @author fengshuonan
 * @date 2020/8/20
 */
public class WxLoginUserTreadLocalContext {

    private static final ThreadLocal<Dict> WX_USER_CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 保存请求参数
     *
     * @author stylefeng
     * @date 2020/6/21 20:17
     */
    public static void set(Dict requestParam) {
        WX_USER_CONTEXT_HOLDER.set(requestParam);
    }

    /**
     * 保存请求参数
     *
     * @author stylefeng
     * @date 2020/6/21 20:17
     */
    public static void setObject(Object requestParam) {

        if (requestParam == null) {
            return;
        }

        if (requestParam instanceof Dict) {
            WX_USER_CONTEXT_HOLDER.set((Dict) requestParam);
        } else {
            WX_USER_CONTEXT_HOLDER.set(Dict.parse(requestParam));
        }
    }

    /**
     * 获取请求参数
     *
     * @author stylefeng
     * @date 2020/6/21 20:17
     */
    public static Dict get() {
        return WX_USER_CONTEXT_HOLDER.get();
    }

    /**
     * 清除请求参数
     *
     * @author stylefeng
     * @date 2020/6/21 20:17
     */
    public static void clear() {
        WX_USER_CONTEXT_HOLDER.remove();
    }
}
