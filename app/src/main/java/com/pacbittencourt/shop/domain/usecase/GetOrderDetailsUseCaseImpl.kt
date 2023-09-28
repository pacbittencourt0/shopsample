package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.orders
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOrderDetailsUseCaseImpl : GetOrderDetailsUseCase {
    override operator fun invoke(orderId: Int): Flow<Order> {
        val index = orderId - 1
        return flow {
            delay(1000L)
            emit(orders[index])
        }
    }
}