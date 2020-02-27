package com.silso.room

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class appli: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@appli)
            modules(listOf())
        }
    }
}