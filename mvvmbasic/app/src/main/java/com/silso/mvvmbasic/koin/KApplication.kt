package com.silso.mvvmbasic.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KApplication)
            modules(listOf())
        }
    }
}