package com.renyu.aabdemo

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

class MainActivity : AppCompatActivity() {

    private lateinit var manager: SplitInstallManager

    private val listener = SplitInstallStateUpdatedListener { state ->
        Toast.makeText(this, "${state.status()}", Toast.LENGTH_SHORT).show()
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {

            }
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {

            }
            SplitInstallSessionStatus.INSTALLED -> {

            }

            SplitInstallSessionStatus.INSTALLING -> {

            }
            SplitInstallSessionStatus.FAILED -> {

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = SplitInstallManagerFactory.create(this)

        findViewById<TextView>(R.id.textview).setOnClickListener {
            if (manager.installedModules.contains("dynamicfeature")) {
                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "NOHello", Toast.LENGTH_SHORT).show()

                val request = SplitInstallRequest.newBuilder()
                    .addModule("dynamicfeature")
                    .build()

                val task = manager.startInstall(request)
                task.addOnCompleteListener {
                    Log.d("TAGTAGTAG", "addOnCompleteListener")
                }
                task.addOnFailureListener {
                    it.printStackTrace()
                }
                task.addOnSuccessListener {
                    Log.d("TAGTAGTAG", "addOnSuccessListener")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        manager.registerListener(listener)
    }

    override fun onPause() {
        super.onPause()
        manager.unregisterListener(listener)
    }
}