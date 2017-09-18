package com.renyu.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.renyu.kotlindemo.bean.User
import com.renyu.kotlindemo.presenter.IUserPresenter
import com.renyu.kotlindemo.presenter.UserPresenter
import com.renyu.kotlindemo.view.IUserView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2017/9/12.
 */
class KotlinMainActivity : AppCompatActivity(), IUserView {

    val presenter: IUserPresenter by lazy {
        UserPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.saveUserInfo("renyu", 30)
        tv_hello.setOnClickListener {
            presenter.getUserInfo()
        }
    }

    override fun updateUserInfo(userInfo: User?) {
        tv_hello.text = "姓名：${userInfo?.name}，年龄：${userInfo?.age}"
    }
}
