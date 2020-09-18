package com.silso.socket

import com.silso.socket.MainActivity.Companion.closed
import com.silso.socket.MainActivity.Companion.mHandler
import com.silso.socket.MainActivity.Companion.server
import com.silso.socket.MainActivity.Companion.socket
import com.silso.socket.MainActivity.Companion.writeSocket

class CloseServer : Thread() {
    override fun run() {
        try {
            closed = true
            writeSocket.write(10)    //클라이언트에게 서버가 종료되었음을 알림
            socket.close()
            server.close()
        } catch (e: Exception) {
            e.printStackTrace()
            mHandler.obtainMessage(8).apply {
                sendToTarget()
            }
        }
    }
}