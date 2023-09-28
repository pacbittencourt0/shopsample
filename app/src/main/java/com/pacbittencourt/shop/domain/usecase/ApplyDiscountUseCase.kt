package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.ItemOrder

interface ApplyDiscountUseCase {
    operator fun invoke(discount: Double)
}

class ApplyDiscountUseCaseImpl : ApplyDiscountUseCase {
    override fun invoke(discount: Double) {
        var total = 0.0
        val auxCart = mutableListOf<ItemOrder>()
        currentCart.forEach {
            total += it.totalPrice
        }
        currentCart.forEach {
            val itemDiscount = (it.totalPrice / total) * discount
            val newPrice = it.totalPrice - itemDiscount
            auxCart.add(it.copy(totalPrice = newPrice))
        }
        currentCart.clear()
        currentCart.addAll(auxCart)
    }
}
