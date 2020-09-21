package com.silso.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val message = "Made Broadcast by Context! " + intent.action
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }

        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver)
            receiver = null
        }
        super.onDestroy()
    }
}