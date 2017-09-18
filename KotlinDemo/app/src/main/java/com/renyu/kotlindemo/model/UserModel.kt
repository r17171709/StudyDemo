package com.renyu.kotlindemo.model

import com.renyu.kotlindemo.bean.User

/**
 * Created by Administrator on 2017/9/18.
 */
class UserModel : IUserModel {

    private val temp: User by lazy {
        User("", 0)
    }

    override fun setUserName(name: String) {
        temp?.name = name
    }

    override fun setAge(age: Int) {
        temp?.age = age
    }

    override fun getUser(): User? {
        return temp
    }
}