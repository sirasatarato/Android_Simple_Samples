package com.silso.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CustomBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        // 배터리 인식
        val message = "Made Broadcast by Manifest! " + p1!!.action
        Toast.makeText(p0, message, Toast.LENGTH_LONG).show()
    }
}