package com.silso.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class BindServiceClass : Service() {
    private val localBinder = LocalBinder()
    private val generator = Random()

    val randomNumber: Int
        get() = generator.nextInt(100)

    inner class LocalBinder : Binder() {
        fun getService(): BindServiceClass = this@BindServiceClass
    }

    override fun onBind(intent: Intent): IBinder {
        return localBinder
    }
}
