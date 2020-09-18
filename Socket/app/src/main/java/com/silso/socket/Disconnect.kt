package com.silso.socket

import com.silso.socket.MainActivity.Companion.socket
import com.silso.socket.MainActivity.Companion.writeSocket

class Disconnect : Thread() {
    override fun run() {
        try {
            writeSocket.write(10)    //서버에게 접속 종료 명령 전송
            socket.close()
        } catch (e: Exception) {

        }
    }
}