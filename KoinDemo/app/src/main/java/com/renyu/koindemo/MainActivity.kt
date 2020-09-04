package com.renyu.koindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.renyu.mylibrary.LibBean
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {
    val bussinessServiceBean: BussinessServiceBean by inject()
    val bussinessServiceBean2 by inject<BussinessServiceBean>()
    val controllerBean by inject<ControllerBean>()
    val controllerBean2 by inject<ControllerBean>()
    val viewModel by inject<MyViewModel>()
    val appData by inject<AppData>()
    val mutiParamBean by inject<MutiParamBean>(named("Application"))
    val mutiParamBean2 by inject<MutiParamBean>(named("String"))
    val mutiParamBean3 by inject<MutiParamBean>(named("MoreParams"))
    val mutiParamBean4 by inject<MutiParamBean>(named("Bussiness"))
    val mutiParamBean5 by inject<MutiParamBean>(named("FromOther")) {
        parametersOf("123")
    }
    val mutiParamBean6 by inject<MutiParamBean>(named("FromOther2")) {
        parametersOf("456", "789")
    }
    val mutiParamBean7 by inject<MutiParamBean>(named("properties"))
    val libBean by inject<LibBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("TAG", bussinessServiceBean.hashCode().toString())
        Log.d("TAG", bussinessServiceBean2.hashCode().toString())
        Log.d("TAG", controllerBean.hashCode().toString())
        Log.d("TAG", controllerBean2.hashCode().toString())
        Log.d("TAG", appData.application.hashCode().toString())
        Log.d("TAG", mutiParamBean.application.hashCode().toString())
        Log.d("TAG", mutiParamBean2.param1)
        Log.d("TAG", "${mutiParamBean3.param1} ${mutiParamBean3.param2}")
        Log.d("TAG", mutiParamBean4.bussinessServiceBean.hashCode().toString())
        Log.d("TAG", mutiParamBean5.param1)
        Log.d("TAG", "${mutiParamBean6.param1} ${mutiParamBean6.param2}")
        Log.d("TAG", "${mutiParamBean7.param1} ${mutiParamBean7.param2}")
        Log.d("TAG", libBean.hashCode().toString())

        Looper.myQueue().addIdleHandler {
            viewModel.showToast(this)
            return@addIdleHandler false
        }

        getScopeData()
        createScopeData()
        getScopeData()
    }

    private fun createScopeData() {
        val scope = getKoin().createScope("scopeId1", named("myScope"))
        bindScope(scope)
        Log.d("TAG",  scope.get<ScopeBean>().hashCode().toString())
    }

    private fun getScopeData() {
        val scope = getKoin().getScopeOrNull("scopeId1")
        val value = scope?.get<ScopeBean>()?.hashCode()?.toString()
        if (value != null) {
            Log.d("TAG", value)
        }
    }
}
