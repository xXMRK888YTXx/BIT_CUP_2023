package com.xxmrk888ytxx.bit_cup_2023.data.repository.network

import com.xxmrk888ytxx.bit_cup_2023.data.networkObserver.NetworkObserver
import com.xxmrk888ytxx.bit_cup_2023.domain.repository.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val networkObserver: NetworkObserver
) : NetworkRepository {
    override val isNetworkAvailable: Flow<Boolean> = networkObserver.isNetworkAvailable
}