package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.orders
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOrdersUseCaseImpl : GetOrdersUseCase {

    override operator fun invoke(): Flow<List<Order>> {
        return flow {
            delay(1000L)
            emit(orders.toList())
        }
    }
}
