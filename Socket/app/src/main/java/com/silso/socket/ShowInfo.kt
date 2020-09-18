package com.silso.socket

import com.silso.socket.MainActivity.Companion.mHandler
import java.net.Inet4Address
import java.net.NetworkInterface

class ShowInfo : Thread() {

    override fun run() {
        lateinit var ip: String
        var breakLoop = false
        val en = NetworkInterface.getNetworkInterfaces()

        while (en.hasMoreElements()) {
            val intf = en.nextElement()
            val enumIpAddr = intf.inetAddresses

            while (enumIpAddr.hasMoreElements()) {
                val inetAddress = enumIpAddr.nextElement()

                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                    ip = inetAddress.hostAddress.toString()
                    breakLoop = true
                    break
                }
            }

            if (breakLoop) break
        }

        val msg = mHandler.obtainMessage()
        msg.what = 9
        msg.obj = ip
        mHandler.sendMessage(msg)
    }
}