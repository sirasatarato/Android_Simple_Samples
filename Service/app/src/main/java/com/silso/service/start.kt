package com.silso.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class start : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("JW_TEST", "onStartCommand()")

        return START_STICKY // 서비스가 강제종료될 시 재생성합니다.
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
