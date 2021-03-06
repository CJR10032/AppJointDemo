package com.chaomeng.appjointdemo.app

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants


/**
 *
 * 创建      CJR
 * 创建时间  2020/06/12 18:07
 * 描述     自定义Application
 *
 * 注意：这个类集成TinkerApplication类，这里面不做任何操作，所有Application的代码都会放到ApplicationLike继承类当中
 * <pre>
 * 参数解析：
 * 参数1：int tinkerFlags 表示Tinker支持的类型 dex only、library only or all suuport，default: TINKER_ENABLE_ALL
 * 参数2：String delegateClassName Application代理类 这里填写你自定义的ApplicationLike
 * 参数3：String loaderClassName  Tinker的加载器，使用默认即可
 * 参数4：boolean tinkerLoadVerifyFlag  加载dex或者lib是否验证md5，默认为false
 */
class CmTinkerApplication : TinkerApplication(
    ShareConstants.TINKER_ENABLE_ALL,
    "com.chaomeng.appjointdemo.app.App",
    "com.tencent.tinker.loader.TinkerLoader",
    false
)