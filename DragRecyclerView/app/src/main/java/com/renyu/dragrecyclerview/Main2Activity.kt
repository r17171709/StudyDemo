package com.renyu.dragrecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.renyu.dragrecyclerview.adapter.StaggerGridAdapter
import com.renyu.dragrecyclerview.bean.ImageBean
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Administrator on 2020/6/18.
 */
class Main2Activity : AppCompatActivity() {
    private val beans: ArrayList<ImageBean> by lazy {
        ArrayList<ImageBean>()
    }
    private val adapter: StaggerGridAdapter by lazy {
        StaggerGridAdapter(beans)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageBean1 = ImageBean()
        imageBean1.width = 600
        imageBean1.height = 450
        imageBean1.id = R.mipmap.pic1
        beans.add(imageBean1)

        val imageBean2 = ImageBean()
        imageBean2.width = 400
        imageBean2.height = 263
        imageBean2.id = R.mipmap.pic2
        beans.add(imageBean2)

        val imageBean3 = ImageBean()
        imageBean3.width = 640
        imageBean3.height = 360
        imageBean3.id = R.mipmap.pic3
        beans.add(imageBean3)

        val imageBean4 = ImageBean()
        imageBean4.width = 800
        imageBean4.height = 1733
        imageBean4.id = R.mipmap.pic4
        beans.add(imageBean4)

        val imageBean5 = ImageBean()
        imageBean5.width = 700
        imageBean5.height = 585
        imageBean5.id = R.mipmap.pic5
        beans.add(imageBean5)

        val imageBean6 = ImageBean()
        imageBean6.width = 540
        imageBean6.height = 393
        imageBean6.id = R.mipmap.pic6
        beans.add(imageBean6)

        val imageBean7 = ImageBean()
        imageBean7.width = 600
        imageBean7.height = 399
        imageBean7.id = R.mipmap.pic7
        beans.add(imageBean7)

        val imageBean8 = ImageBean()
        imageBean8.width = 600
        imageBean8.height = 450
        imageBean8.id = R.mipmap.pic8
        beans.add(imageBean8)

        val imageBean9 = ImageBean()
        imageBean9.width = 600
        imageBean9.height = 853
        imageBean9.id = R.mipmap.pic9
        beans.add(imageBean9)

        val imageBean10 = ImageBean()
        imageBean10.width = 640
        imageBean10.height = 432
        imageBean10.id = R.mipmap.pic10
        beans.add(imageBean10)

        val layoutmanager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv.layoutManager = layoutmanager
        rv.setHasFixedSize(true)
        rv.adapter = adapter
    }
}