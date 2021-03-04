package com.renyu.recyclerviewdemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.renyu.recyclerviewdemo.adapter.ConcatSectionAdapter
import com.renyu.recyclerviewdemo.databinding.ActivityConcatBinding
import com.renyu.recyclerviewdemo.util.enforceSingleScrollDirection

/**
 * 参考文章 https://juejin.cn/post/6934810205968400398
 */
class ConcatActivity : AppCompatActivity() {
    private var binding: ActivityConcatBinding? = null
    private val beans by lazy { ArrayList<ArrayList<String>>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcatBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val concatAdapter = ConcatAdapter()
        for (i in 0 until 20) {
            val tmp = ArrayList<String>()
            for (j in 0 until 5) {
                tmp.add("$j")
            }
            beans.add(tmp)
        }
        val sectionAdapter = ConcatSectionAdapter(beans)
        concatAdapter.addAdapter(sectionAdapter)
        binding!!.rvConcat.apply {
            layoutManager = LinearLayoutManager(this@ConcatActivity)
            adapter = concatAdapter
            enforceSingleScrollDirection()
        }
    }
}