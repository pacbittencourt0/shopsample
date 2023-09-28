package com.pacbittencourt.shop.domain.usecase

interface AddItemUseCase {
    operator fun invoke(id: Int, quantity: Double): Boolean
}