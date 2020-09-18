package com.silso.socket

import com.silso.socket.MainActivity.Companion.ip
import com.silso.socket.MainActivity.Companion.mHandler
import com.silso.socket.MainActivity.Companion.port
import com.silso.socket.MainActivity.Companion.readSocket
import com.silso.socket.MainActivity.Companion.socket
import com.silso.socket.MainActivity.Companion.writeSocket
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class Connect : Thread() {
    override fun run() {
        try {
            socket = Socket(ip, port)
            writeSocket = DataOutputStream(socket.getOutputStream())
            readSocket = DataInputStream(socket.getInputStream())

            val b = readSocket.read()
            if (b == 1) {    //서버로부터 접속이 확인되었을 때
                mHandler.obtainMessage(11).apply {
                    sendToTarget()
                }
                ClientSocket().start()
            } else {    //서버 접속에 성공하였으나 서버가 응답을 하지 않았을 때
                mHandler.obtainMessage(14).apply {
                    sendToTarget()
                }
                socket.close()
            }
        } catch (e: Exception) {    //연결 실패
            val state = 1
            mHandler.obtainMessage(state).apply {
                sendToTarget()
            }
            socket.close()
        }
    }
}