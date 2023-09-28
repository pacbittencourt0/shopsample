package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.currentCart

class ClearCartUseCaseImpl : ClearCartUseCase {
    override operator fun invoke() {
        currentCart.clear()
    }
}
