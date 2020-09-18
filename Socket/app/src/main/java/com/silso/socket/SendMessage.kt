package com.silso.socket

import com.silso.socket.MainActivity.Companion.mHandler
import com.silso.socket.MainActivity.Companion.writeSocket

class SendMessage : Thread() {
    private lateinit var msg: String

    fun setMsg(m: String) {
        msg = m
    }

    override fun run() {
        try {
            writeSocket.writeInt(2)    //메시지 전송 명령 전송
            writeSocket.writeUTF(msg)    //메시지 내용
        } catch (e: Exception) {
            e.printStackTrace()
            mHandler.obtainMessage(12).apply {
                sendToTarget()
            }
        }
    }
}