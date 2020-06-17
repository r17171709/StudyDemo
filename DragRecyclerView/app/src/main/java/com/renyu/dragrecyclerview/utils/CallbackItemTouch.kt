package com.renyu.dragrecyclerview.utils

/**
 * Created by Administrator on 2020/6/17.
 */
interface CallbackItemTouch {
    fun onMove(
        oldPosition: Int,
        newPosition: Int
    )
}