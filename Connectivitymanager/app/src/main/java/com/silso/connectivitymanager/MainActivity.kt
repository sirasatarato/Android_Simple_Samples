package com.silso.connectivitymanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val networkConnectionStateMonitor: NetworkConnectionStateMonitor by lazy {
        NetworkConnectionStateMonitor(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkConnectionStateMonitor.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectionStateMonitor.unregister()
    }
}