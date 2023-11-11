package com.xxmrk888ytxx.bit_cup_2023.domain.useCase

import com.xxmrk888ytxx.bit_cup_2023.domain.repository.network.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInternetStateUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke() : Flow<Boolean> = networkRepository.isNetworkAvailable
}