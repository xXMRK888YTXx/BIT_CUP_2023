package com.xxmrk888ytxx.bit_cup_2023.data.networkObserver

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkObserver @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val _isNetworkAvailable = MutableStateFlow(false)
    val isNetworkAvailable = _isNetworkAvailable.asStateFlow()

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager

    private val activeConnections = mutableSetOf<Int>()

    private fun updateState() {
        _isNetworkAvailable.update { activeConnections.isNotEmpty() }
    }

    private fun onNetworkAvailable(networkId: Int) {
        activeConnections.add(networkId)
        updateState()
    }

    fun onNetworkLost(networkId: Int) {
        activeConnections.remove(networkId)
        updateState()
    }

    fun onNetworkUnavailable() {
        activeConnections.clear()
        updateState()
    }

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            onNetworkAvailable(network.hashCode())
        }

        override fun onLost(network: Network) {
            onNetworkLost(network.hashCode())
        }

        override fun onUnavailable() {
            onNetworkUnavailable()
        }
    }

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)
    }
}