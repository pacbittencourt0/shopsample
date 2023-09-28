package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.ItemOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrentCartUseCaseImpl : GetCurrentCartUseCase {

    override operator fun invoke(): Flow<List<ItemOrder>> {
        return flow {
            delay(1000L)
            emit(currentCart)
        }
    }
}