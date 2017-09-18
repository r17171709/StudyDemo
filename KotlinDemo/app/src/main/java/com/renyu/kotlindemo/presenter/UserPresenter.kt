package com.renyu.kotlindemo.presenter

import com.renyu.kotlindemo.model.IUserModel
import com.renyu.kotlindemo.model.UserModel
import com.renyu.kotlindemo.view.IUserView

/**
 * Created by Administrator on 2017/9/18.
 */
class UserPresenter(val iUserView: IUserView) : IUserPresenter {

    val iUserModel: IUserModel by lazy {
        UserModel()
    }

    override fun saveUserInfo(name: String, age: Int) {
        iUserModel.setAge(age)
        iUserModel.setUserName(name)
    }

    override fun getUserInfo() {
        iUserView.updateUserInfo(iUserModel.getUser())
    }
}