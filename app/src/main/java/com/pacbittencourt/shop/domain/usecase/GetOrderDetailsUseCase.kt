package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface GetOrderDetailsUseCase {
    operator fun invoke(orderId: Int): Flow<Order>
}