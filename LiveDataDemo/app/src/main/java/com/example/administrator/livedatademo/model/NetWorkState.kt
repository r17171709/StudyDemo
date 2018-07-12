package com.example.administrator.livedatademo.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import com.blankj.utilcode.util.NetworkUtils

class NetWorkState(val context: Context) : LiveData<Boolean>() {

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            value = NetworkUtils.isConnected()
        }
    }

    override fun onInactive() {
        super.onInactive()

        context.unregisterReceiver(broadcastReceiver)
    }

    override fun onActive() {
        super.onActive()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(broadcastReceiver, intentFilter)
    }
}