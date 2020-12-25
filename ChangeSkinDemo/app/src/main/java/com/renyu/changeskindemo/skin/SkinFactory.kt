package com.renyu.changeskindemo.skin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.renyu.changeskindemo.R

class SkinFactory : LayoutInflater.Factory2 {
    private lateinit var delegate: AppCompatDelegate

    private val skinViewList by lazy { ArrayList<SkinView>() }

    fun setDelegate(delegate: AppCompatDelegate) {
        this.delegate = delegate
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view = delegate.createView(parent, name, context, attrs)
        // 万一系统创建出来是空，那么我们来补救
        if (view == null) {
            val utils = Utils()
            view = utils.createViewFromTag(name, context, attrs)
        }
        // 收集需要换肤的View
        collectSkinView(context, attrs, view)
        return view
    }

    /**
     * Factory2是继承Factory的，所以主要重写Factory2的onCreateView，不必理会Factory的重写方法了
     */
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    /**
     * 通过自定义属性isSupport，从创建出来的很多View中找到支持换肤的那些保存到map中
     */
    private fun collectSkinView(context: Context, attrs: AttributeSet, view: View) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.Skinable)
        val isSupport = typedArray.getBoolean(R.styleable.Skinable_isSupport, false)
        if (isSupport) {
            val length = attrs.attributeCount
            val attrMap = HashMap<String, String>()
            // 遍历所有属性
            for (i in 0 until length) {
                val attrName = attrs.getAttributeName(i)
                val attrValue = attrs.getAttributeValue(i)
                attrMap[attrName] = attrValue
            }
            val skinView = SkinView(view, attrMap)
            skinViewList.add(skinView)
        }
        typedArray.recycle()
    }

    fun changeSkin() {
        skinViewList.forEach {
            it.changeSkin()
        }
    }
}