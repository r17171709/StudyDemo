package com.renyu.changeskindemo.skin

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.renyu.changeskindemo.R

class SkinView(private val view: View, private val attrsMap: HashMap<String, String>) {
    /**
     * 真正的换肤操作
     */
    fun changeSkin() {
        if (view is TextView) {
            if (!TextUtils.isEmpty(attrsMap["textColor"])) {
                val resId = attrsMap["textColor"]!!.substring(1).toInt()
                val resName = view.resources.getResourceEntryName(resId)
                view.setTextColor(SkinEngine.instance.getColor(resName))
            }
        }
    }
}