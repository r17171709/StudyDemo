package com.renyu.listvisibilitydemo

/**
 * Created by Administrator on 2018/2/24 0024.
 */
interface ListItem {
    // 当前显示项中的某一项从不完全显示到完全显示时使用
    fun setActive()
    // 当前显示项中的某一项从完全显示到不完全显示时使用
    fun deActive()
    // 设置进度
    fun setPercent(percent: Int)
}