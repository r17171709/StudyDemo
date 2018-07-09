package com.example.administrator.databindingdemo2.util

import android.view.View
import android.widget.Toast

class MyHandlers {
    fun onClick(view: View) {
        Toast.makeText(view.context, "MyHandlers onClick", Toast.LENGTH_SHORT).show()
    }

    fun onClick3(view: View, str: String) {
        Toast.makeText(view.context, str, Toast.LENGTH_SHORT).show()
    }
}