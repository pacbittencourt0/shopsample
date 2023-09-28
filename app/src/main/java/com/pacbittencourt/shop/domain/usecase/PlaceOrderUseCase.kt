package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.Order

interface PlaceOrderUseCase {
    operator fun invoke(order: Order): Boolean
}