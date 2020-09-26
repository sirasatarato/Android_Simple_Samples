package com.silso.lowbluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.LeScanCallback
import android.bluetooth.BluetoothManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var scanning = false
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bleList: BleList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        bleList = BleList(this)
        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        checkBlueToothOn()
        listView.adapter = bleList
        setClick()
    }

    private fun checkPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BLUETOOTH), 1)
    }

    private fun checkBlueToothOn() {
        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(this, "블루투스를 켜주세요", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setClick() {
        val leScanCallback = LeScanCallback { device, rssi, scanRecord ->
            Log.d("scan", device.name + " RSSI :" + rssi + " Record " + scanRecord)
            bleList.addDevice(device, rssi)
            bleList.notifyDataSetChanged()
        }

        button.setOnClickListener {
            if (!scanning) {
                bluetoothAdapter.startLeScan(leScanCallback)
            } else {
                bluetoothAdapter.stopLeScan(leScanCallback)
                bleList.clear()
                bleList.notifyDataSetChanged()
            }
            scanning = !scanning
        }
    }
}