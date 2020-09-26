package com.silso.lowbluetooth

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class BleList(activity: Activity) : BaseAdapter() {
    private val devices: ArrayList<BluetoothDevice> = ArrayList()
    private val RSSIs: ArrayList<Int> = ArrayList()
    private val inflater: LayoutInflater = (activity).layoutInflater

    override fun getCount(): Int = devices.size
    override fun getItem(position: Int): Any = devices[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView1: View? = convertView
        val viewHolder: ViewHolder

        if (convertView1 == null) {
            viewHolder = ViewHolder()
            convertView1 = inflater.inflate(R.layout.two_line_list_item, parent)
            viewHolder.deviceName = convertView1.findViewById(R.id.text1)
            viewHolder.deviceRssi = convertView1.findViewById(R.id.text2)
            convertView1?.tag = viewHolder
        } else {
            viewHolder = convertView1.tag as ViewHolder
        }
        val deviceName = devices[position].name
        val rssi = RSSIs[position]
        viewHolder.deviceName!!.text =
            if (deviceName != null && deviceName.isNotEmpty()) deviceName else "알 수 없는 장치"
        viewHolder.deviceRssi!!.text = rssi.toString()
        return convertView1
    }

    fun addDevice(device: BluetoothDevice, rssi: Int) {
        if (!devices.contains(device)) {
            devices.add(device)
            RSSIs.add(rssi)
        } else {
            RSSIs[devices.indexOf(device)] = rssi
        }
    }

    fun clear() {
        devices.clear()
        RSSIs.clear()
    }
}