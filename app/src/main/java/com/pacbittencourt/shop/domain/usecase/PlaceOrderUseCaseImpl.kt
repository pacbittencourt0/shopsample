package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.orders

class PlaceOrderUseCaseImpl : PlaceOrderUseCase {
    override operator fun invoke(order: Order): Boolean {
        val newOrder = order.copy(
            items = order.items.map {
                ItemOrder(
                    item = it.item,
                    quantity = it.quantity,
                    totalPrice = it.totalPrice
                )
            }
        )
        return if (orders.add(newOrder)) {
            currentCart.clear()
            true
        } else false
    }
}