package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.currentCart

interface DeleteItemUseCase {
    operator fun invoke(index: Int)
}

class DeleteItemUseCaseImpl : DeleteItemUseCase {
    override fun invoke(index: Int) {
        currentCart.removeAt(index)
    }
}
