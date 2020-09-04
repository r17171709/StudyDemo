package com.renyu.koindemo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

/**
 * Created by Administrator on 2020/9/4.
 */
class MyViewModel : ViewModel() {
    fun showToast(context: Context) {
        Toast.makeText(context, "ViewModel", Toast.LENGTH_SHORT).show()
    }
}