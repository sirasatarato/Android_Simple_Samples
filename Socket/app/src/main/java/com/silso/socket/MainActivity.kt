package com.silso.socket

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {
    companion object {
        var socket = Socket()
        var server = ServerSocket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream

        var ip = "192.168.0.1"
        var port = 2222
        lateinit var mHandler: Handler
        var closed = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        server.close()
        socket.close()

        button_connect.setOnClickListener {    //클라이언트 -> 서버 접속
            if (et_ip.text.isNotEmpty()) {
                ip = et_ip.text.toString()
                if (et_port.text.isNotEmpty()) {
                    port = et_port.text.toString().toInt()
                    if (port < 0 || port > 65535) {
                        toast("PORT 번호는 0부터 65535까지만 가능합니다.")
                    } else {
                        if (!socket.isClosed) {
                            toast("${ip}에 이미 연결되어 있습니다.")
                        } else {
                            Connect().start()
                        }
                    }

                } else {
                    toast("PORT 번호를 입력해주세요.")
                }
            } else {
                toast("IP 주소를 입력해주세요.")
            }
        }

        button_disconnect.setOnClickListener {    //클라이언트 -> 서버 접속 끊기
            if (!socket.isClosed) {
                Disconnect().start()
            } else {
                toast("서버와 연결이 되어있지 않습니다.")
            }
        }

        button_setserver.setOnClickListener {    //서버 포트 열기
            if (et_port.text.isNotEmpty()) {
                val cport = et_port.text.toString().toInt()
                if (cport < 0 || cport > 65535) {
                    toast("PORT 번호는 0부터 65535까지만 가능합니다.")
                } else {
                    if (server.isClosed) {
                        port = cport
                        SetServer().start()
                    } else {
                        toast("${port}번 포트가 열려있습니다.")
                    }
                }
            } else {
                toast("PORT 번호를 입력해주세요.")
            }
        }

        button_closeserver.setOnClickListener {    //서버 포트 닫기
            if (!server.isClosed) {
                CloseServer().start()
            } else {
                mHandler.obtainMessage(17).apply {
                    sendToTarget()
                }
            }
        }

        button_info.setOnClickListener {    //자기자신의 연결 정보(IP 주소)확인
            ShowInfo().start()
        }

        button_msg.setOnClickListener {    //상대에게 메시지 전송
            if (socket.isClosed) {
                toast("연결이 되어있지 않습니다.")
            } else {
                val mThread = SendMessage()
                mThread.setMsg(et_msg.text.toString())
                mThread.start()
            }
        }

        mHandler = object : Handler(Looper.getMainLooper()) {  //Thread들로부터 Handler를 통해 메시지를 수신
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    1 -> toast("IP 주소가 잘못되었거나 서버의 포트가 개방되지 않았습니다.")
                    2 -> toast("서버 포트 ${port}가 준비되었습니다.")
                    3 -> toast(msg.obj.toString())
                    4 -> toast("연결이 종료되었습니다.")
                    5 -> toast("이미 사용중인 포트입니다.")
                    6 -> toast("서버 준비에 실패하였습니다.")
                    7 -> toast("서버가 종료되었습니다.")
                    8 -> toast("서버가 정상적으로 닫히는데 실패하였습니다.")
                    9 -> text_status.text = msg.obj as String
                    11 -> toast("서버에 접속하였습니다.")
                    12 -> toast("메시지 전송에 실패하였습니다.")
                    13 -> toast("클라이언트와 연결되었습니다.")
                    14 -> toast("서버에서 응답이 없습니다.")
                    15 -> toast("서버와의 연결을 종료합니다.")
                    16 -> toast("클라이언트와의 연결을 종료합니다.")
                    17 -> toast("포트가 이미 닫혀있습니다.")
                    18 -> toast("서버와의 연결이 끊어졌습니다.")
                }
            }
        }
    }
}