package com.renyu.kotlindemo.model

import com.renyu.kotlindemo.bean.User

/**
 * Created by Administrator on 2017/9/18.
 */
interface IUserModel {
    fun setUserName(name: String)
    fun setAge(age: Int)
    fun getUser() : User?
}