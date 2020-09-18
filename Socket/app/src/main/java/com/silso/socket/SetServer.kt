package com.silso.socket

import com.silso.socket.MainActivity.Companion.closed
import com.silso.socket.MainActivity.Companion.mHandler
import com.silso.socket.MainActivity.Companion.port
import com.silso.socket.MainActivity.Companion.readSocket
import com.silso.socket.MainActivity.Companion.server
import com.silso.socket.MainActivity.Companion.socket
import com.silso.socket.MainActivity.Companion.writeSocket
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.BindException
import java.net.ServerSocket
import java.net.SocketException

class SetServer : Thread() {

    override fun run() {
        try {
            server = ServerSocket(port)    //포트 개방
            mHandler.obtainMessage(2, "").apply {
                sendToTarget()
            }

            while (true) {
                socket = server.accept()
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                writeSocket.write(1)    //클라이언트에게 서버의 소켓 생성을 알림
                mHandler.obtainMessage(13).apply {
                    sendToTarget()
                }
                while (true) {
                    val ac = readSocket.read()
                    if (ac == 10) {    //클라이언트로부터 소켓 종료 명령 수신
                        mHandler.obtainMessage(16).apply {
                            sendToTarget()
                        }
                        break
                    } else if (ac == 2) {    //클라이언트로부터 메시지 전송 명령 수신
                        val bac = readSocket.readUTF()
                        val input = bac.toString()
                        val recvInput = input.trim()

                        val msg = mHandler.obtainMessage()
                        msg.what = 3
                        msg.obj = recvInput
                        mHandler.sendMessage(msg)    //핸들러에게 클라이언트로 전달받은 메시지 전송
                    }
                }
            }

        } catch (e: BindException) {    //이미 개방된 포트를 개방하려 시도하였을때
            mHandler.obtainMessage(5).apply {
                sendToTarget()
            }
        } catch (e: SocketException) {    //소켓이 닫혔을 때
            mHandler.obtainMessage(7).apply {
                sendToTarget()
            }
        } catch (e: Exception) {
            if (!closed) {
                mHandler.obtainMessage(6).apply {
                    sendToTarget()
                }
            } else {
                closed = false
            }
        }
    }
}