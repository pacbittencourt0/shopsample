package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface GetOrdersUseCase {
    operator fun invoke(): Flow<List<Order>>
}