package com.silso.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var bs: BindingService? = null
    private val mMusicPlayerServiceConnection = object: ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            bs = null
        }
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            bs = (p1 as BindingService.MusicPlayerBinder).getService()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonPlay.setOnClickListener { start(1) }
        buttonPause.setOnClickListener { start(2) }
        buttonStop.setOnClickListener { start(3) }
    }

    fun start(int: Int) {
        bs?.stop()
        bs?.play(int)
    }

    override fun onResume() {
        super.onResume()

        if (bs == null) {
            onBindService()
        }
        startService(Intent(applicationContext, BindingService::class.java))
    }

    override fun onPause() {
        super.onPause()

        if (bs != null) {
            if (bs?.isPlaying() == true) {
                // 음악이 정지 중일 경우는 서비스를 계속 실행할 필요가 없으므로 정지한다.
                bs!!.stopSelf()
            }
            unbindService(mMusicPlayerServiceConnection)
            bs = null
        }
    }

    fun onBindService() {
        val intent = Intent(this, BindingService::class.java)
        bindService(intent, mMusicPlayerServiceConnection, Context.BIND_AUTO_CREATE)
    }
}