package com.renyu.koindemo

import android.app.Application
import com.renyu.mylibrary.LibModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Administrator on 2020/9/4.
 * 参考文章：https://www.jianshu.com/p/bccb93a78cee
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // 传入Application对象，这样在注入的类中可以直接使用app对象
            androidContext(this@MyApp)
            androidFileProperties()
            modules(appModule, LibModule.lib)
        }
        loadKoinModules(otherModule)
    }

    // 各种注入对象
    private val appModule = module {
        // 普通注入方式
        factory {
            BussinessServiceBean()
        }
        factory(named("BussinessServiceBean")) {
            BussinessServiceBean()
        }
        // 单例用法
        single {
            ControllerBean(get())
        }
        // viewmodel用法
        viewModel {
            MyViewModel()
        }
        // 添加了Application数据
        factory {
            AppData(get())
        }
        // 多构造方法参数传递
        factory(named("Application")) {
            MutiParamBean(get<Application>())
        }
        factory(named("String")) {
            MutiParamBean("param3")
        }
        factory(named("MoreParams")) {
            MutiParamBean("param1", "param2")
        }
        // 传入指定对象类型
        factory(named("Bussiness")) {
            MutiParamBean(get<BussinessServiceBean>(named("BussinessServiceBean")))
        }
        // 多参数外部调用
        factory(named("FromOther")) { (s: String) ->
            MutiParamBean(s)
        }
        factory(named("FromOther2")) { (s1: String, s2: String) ->
            MutiParamBean(s1, s2)
        }
        factory(named("properties")) {
            MutiParamBean(getProperty("param1"), getProperty("param2"))
        }
        // scope方式
        scope(named("myScope")) {
            scoped {
                ScopeBean()
            }
        }
    }

    private val otherModule = module {

    }
}