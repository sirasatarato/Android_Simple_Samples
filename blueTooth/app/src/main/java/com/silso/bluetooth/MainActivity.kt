package com.silso.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import app.akexorcist.bluetotohspp.library.DeviceList
import kotlinx.android.synthetic.main.activity_main.*

//https://blog.codejun.space/13
//https://hyoin1223.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EB%B8%94%EB%A3%A8%ED%88%AC%EC%8A%A4-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D

class MainActivity : AppCompatActivity() {
    private lateinit var bt: BluetoothSPP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bt = BluetoothSPP(this) //Initializing

        if (!bt.isBluetoothAvailable) { //블루투스 사용 불가
            Toast.makeText(applicationContext
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show()
            finish()
        }

        bt.setOnDataReceivedListener { _, message -> Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show() }

        bt.setBluetoothConnectionListener(object: BluetoothSPP.BluetoothConnectionListener{
            override fun onDeviceDisconnected() { //연결해제
                Toast.makeText(applicationContext
                        , "Connection lost", Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnected(name: String?, address: String?) { //연결됐을 때
                Toast.makeText(applicationContext
                        , "Connected to $name\n$address"
                        , Toast.LENGTH_SHORT).show()
            }

            override fun onDeviceConnectionFailed() { //연결실패
                Toast.makeText(applicationContext
                        , "Unable to connect", Toast.LENGTH_SHORT).show()
            }
        })

        btnConnect.setOnClickListener {
            if (bt.serviceState == BluetoothState.STATE_CONNECTED) {
                bt.disconnect()
            } else {
                startActivityForResult(Intent(applicationContext, DeviceList::class.java), BluetoothState.REQUEST_CONNECT_DEVICE)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bt.stopService() //블루투스 중지
    }

    override fun onStart() {
        super.onStart()
        if (!bt.isBluetoothEnabled) { //
            startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), BluetoothState.REQUEST_ENABLE_BT)
        } else {
            if (!bt.isServiceAvailable) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER) //DEVICE_ANDROID는 안드로이드 기기 끼리
                setup()
            }
        }
    }

    private fun setup() {
        btnSend.setOnClickListener { bt.send("Text", true) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK)
                bt.connect(data)
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService()
                bt.startService(BluetoothState.DEVICE_OTHER)
                setup()
            } else {
                Toast.makeText(applicationContext
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
