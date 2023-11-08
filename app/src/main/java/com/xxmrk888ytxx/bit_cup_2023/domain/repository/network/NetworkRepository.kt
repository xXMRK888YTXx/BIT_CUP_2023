package com.xxmrk888ytxx.bit_cup_2023.domain.repository.network

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    val isNetworkAvailable: Flow<Boolean>
}