package com.renyu.kotlindemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renyu.kotlindemo.bean.Test
import com.renyu.kotlindemo.bean.User
import com.renyu.kotlindemo.model.DelegateGamePlayer
import com.renyu.kotlindemo.model.RealGamePlayer
import com.renyu.kotlindemo.presenter.IUserPresenter
import com.renyu.kotlindemo.presenter.UserPresenter
import com.renyu.kotlindemo.view.IUserView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2017/9/12.
 */
class KotlinMainActivity : AppCompatActivity(), IUserView {

    private val presenter: IUserPresenter by lazy {
        UserPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.saveUserInfo("renyu", 30)
        tv_hello.setOnClickListener {
            presenter.getUserInfo()
        }

        // 代理
        val realGamePlayer = RealGamePlayer()
        val delegateGamePlayer = DelegateGamePlayer(realGamePlayer)
        delegateGamePlayer.rank()
        delegateGamePlayer.upgrade()

        val test = Test()
        println(test.delegate1)
        test.delegate2 = 10
        println(test.delegate2)
    }

    override fun updateUserInfo(userInfo: User?) {
        tv_hello.text = "姓名：${userInfo?.name}，年龄：${userInfo?.age}"
    }
}
