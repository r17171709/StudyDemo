package com.renyu.kotlindemo.presenter

/**
 * Created by Administrator on 2017/9/18.
 */
interface IUserPresenter {
    fun saveUserInfo(name: String, age: Int)
    fun getUserInfo()
}