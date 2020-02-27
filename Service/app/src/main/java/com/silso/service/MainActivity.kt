package com.silso.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.silso.service.start
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var bindingService: bind
    private var bound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            startService(Intent(this, com.silso.service.start::class.java))
        }

        // 버튼이 눌렀을 때 서비스와 상호작용하도록 리스너 등록
        bind.setOnClickListener {
            if (bound) {
                val num: Int = bindingService.randomNumber
                Toast.makeText(this, "number: $num", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val serviceConnection = object : ServiceConnection {
        // 서비스와 연결되었을 때
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as bind.LocalBinder
            bindingService = binder.getService()
            bound = true
            Log.d("bind", "true")
        }

        // 서비스와 연결해제되었을 때
        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
            Log.d("bind", "false")
        }
    }

    override fun onStart() {
        super.onStart()

        // 바인딩
        val intent = Intent(this, bind::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        Log.d("bind", "start")
    }

    override fun onStop() {
        super.onStop()

        // 언바인딩
        unbindService(serviceConnection)
        bound = false
        Log.d("bind", "stop")
    }
}
