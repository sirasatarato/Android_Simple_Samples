package com.silso.socket

import com.silso.socket.MainActivity.Companion.mHandler
import com.silso.socket.MainActivity.Companion.readSocket
import com.silso.socket.MainActivity.Companion.socket
import java.net.SocketException

class ClientSocket : Thread() {
    override fun run() {
        try {
            while (true) {
                val ac = readSocket.read()
                if (ac == 2) {    //서버로부터 메시지 수신 명령을 받았을 때
                    val bac = readSocket.readUTF()
                    val input = bac.toString()
                    val recvInput = input.trim()

                    val msg = mHandler.obtainMessage()
                    msg.what = 3
                    msg.obj = recvInput
                    mHandler.sendMessage(msg)
                } else if (ac == 10) {    //서버로부터 접속 종료 명령을 받았을 때
                    mHandler.obtainMessage(18).apply {
                        sendToTarget()
                    }
                    socket.close()
                    break
                }
            }
        } catch (e: SocketException) {    //소켓이 닫혔을 때
            mHandler.obtainMessage(15).apply {
                sendToTarget()
            }
        }
    }
}