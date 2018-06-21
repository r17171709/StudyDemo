package com.renyu.architecturedemo.mvvm.net

import com.blankj.utilcode.util.Utils
import com.renyu.architecturedemo.MyApplication
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Request

class NetRequest private constructor() {

    companion object {
        @Volatile private var INSTANCE: NetRequest? = null

        fun getInstance(): NetRequest {
            return INSTANCE ?: synchronized(NetRequest::class) {
                INSTANCE ?: NetRequest()
            }
        }
    }

    fun getRequestInfo() : Observable<String> {
        return Observable.create<String> {
            val builder = Request.Builder()
            val request = builder.url("https://www.mocky.io/v2/5afe76b83200007400222e7b").build()
            val call = (Utils.getApp() as MyApplication).client!!.newCall(request)
            val response = call.execute()
            if (response.isSuccessful) {
                it.onNext(response.body()!!.string())
            }
            else {
                it.onError(Exception("出现异常"))
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}