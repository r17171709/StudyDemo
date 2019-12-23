package com.renyu.architecturedemo.util

/**
 * Created by Administrator on 2018/7/17.
 */
data class Resource<T>(val status: Status, val data: T?, val exception: NetworkException?) {
    companion object {
        fun <T> sucess(data: T?): Resource<T> {
            return Resource(Status.SUCESS, data, null)
        }

        fun <T> error(exception: NetworkException): Resource<T> {
            return Resource(Status.ERROR, null, exception)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}