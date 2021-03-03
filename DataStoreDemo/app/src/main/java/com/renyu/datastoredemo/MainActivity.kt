package com.renyu.datastoredemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferenceName = "test"
        val keyName = stringPreferencesKey("key")
        val dataStore = applicationContext.createDataStore(preferenceName)
        GlobalScope.launch {
            dataStore.edit {
                it[keyName] = "abc"
            }

            dataStore.data.map {
                it[keyName]
            }.collect {
                Log.d("TAGTAGTAG", it!!)
            }

            dataStore.edit {
                it.clear()
            }
        }
    }
}