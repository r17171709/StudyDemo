package com.renyu.listvisibilitydemo

import android.graphics.Rect
import android.view.View

/**
 * Created by Administrator on 2018/2/24 0024.
 */
class Utils {
    companion object {
        /**
         * 返回显示的比例
         */
        fun getVisibilityPercents(view: View) : Int {
            val rect = Rect()
            val height = if (view == null || view.visibility != View.VISIBLE) 0 else view.height
            if (height == 0) {
                return 0
            }
            if (view.getLocalVisibleRect(rect)) {
                return when {
                    viewIsPartiallyHiddenTop(rect) -> {
                        (height-rect.top)*100/height
                    }
                    viewIsPartiallyHiddenBottom(rect, height) -> {
                        rect.bottom*100/height
                    }
                    else -> 100
                }
            }
            return 0
        }

        /**
         * 底部视图被遮挡
         */
        private fun viewIsPartiallyHiddenBottom(currentViewRect: Rect, height: Int) = currentViewRect.bottom in 1..(height - 1)

        /**
         * 顶部视图被遮挡
         */
        private fun viewIsPartiallyHiddenTop(currentViewRect: Rect) = currentViewRect.top>0
    }
}