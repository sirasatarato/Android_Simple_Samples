package com.silso.connectivitymanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast

class NetworkConnectionStateMonitor(context: Context) : ConnectivityManager.NetworkCallback() {
    private var context: Context? = null
    private var networkRequest: NetworkRequest? = null
    private var connectivityManager: ConnectivityManager? = null

    init {
        this.context = context
        this.networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build()
    }

    fun register() {
        connectivityManager =
            context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager!!.registerNetworkCallback(networkRequest!!, this)
    }

    fun unregister() {
        connectivityManager!!.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        Toast.makeText(context, "Connected to the network", Toast.LENGTH_SHORT).show()
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        Toast.makeText(context, "Disconnected from the network", Toast.LENGTH_SHORT).show()
    }
}