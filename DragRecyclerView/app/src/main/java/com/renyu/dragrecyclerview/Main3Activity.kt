package com.renyu.dragrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.renyu.dragrecyclerview.adapter.StaggerGridAdapter3
import com.renyu.dragrecyclerview.bean.ImageBean
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2020/6/18.
 */
class Main3Activity : AppCompatActivity() {
    private val beans: ArrayList<Any> by lazy {
        ArrayList<Any>()
    }
    private val adapter: StaggerGridAdapter3 by lazy {
        StaggerGridAdapter3(beans)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beans.add("")

        val imageBean1 = ImageBean()
        imageBean1.width = 640
        imageBean1.height = 853
        imageBean1.url =
            "http://pics5.baidu.com/feed/8cb1cb13495409233754785226d5040fb3de4975.jpeg?token=bb671bd1a1dd831f88ccdfd88deaa211"
        beans.add(imageBean1)

        val imageBean2 = ImageBean()
        imageBean2.width = 900
        imageBean2.height = 602
        imageBean2.url =
            "http://www.xinhuanet.com/politics/leaders/2020-06/17/1126127508_15924152564741n.jpg"
        beans.add(imageBean2)

        beans.add("")

        val imageBean3 = ImageBean()
        imageBean3.width = 669
        imageBean3.height = 403
        imageBean3.url =
            "http://p1.img.cctvpic.com/cportal/cnews-yz/img/2020/06/17/1592383380666_105_669x403.png"
        beans.add(imageBean3)

        val imageBean4 = ImageBean()
        imageBean4.width = 800
        imageBean4.height = 1733
        imageBean4.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/cbf87f3527ac8b4a69dc6d4675f86c79.jpg"
        beans.add(imageBean4)

        val imageBean5 = ImageBean()
        imageBean5.width = 700
        imageBean5.height = 513
        imageBean5.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/f2db249f8cf55a0591ab792bf91f3ce1.jpg"
        beans.add(imageBean5)

        beans.add("")

        val imageBean6 = ImageBean()
        imageBean6.width = 745
        imageBean6.height = 354
        imageBean6.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/841074c175e230fe260e739dbb6d540au1.png"
        beans.add(imageBean6)

        val imageBean7 = ImageBean()
        imageBean7.width = 500
        imageBean7.height = 302
        imageBean7.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/aa60f4bf7e9b61c665d5c961d339c849u5.jpg"
        beans.add(imageBean7)

        val imageBean8 = ImageBean()
        imageBean8.width = 1008
        imageBean8.height = 567
        imageBean8.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/fa90fca85e6f11abf39630b1ad980908.jpg"
        beans.add(imageBean8)

        val imageBean9 = ImageBean()
        imageBean9.width = 585
        imageBean9.height = 587
        imageBean9.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/8bb49c716f4f4fea954dc6edfdfe06dbu1.png"
        beans.add(imageBean9)

        val imageBean10 = ImageBean()
        imageBean10.width = 988
        imageBean10.height = 510
        imageBean10.url =
            "https://m1-1253159997.image.myqcloud.com/imageDir/dfae0df89f1a967d75e6cf69a21c6d31.png"
        beans.add(imageBean10)

        val layoutmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv.layoutManager = layoutmanager
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }
}