package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.ItemOrder

class AddItemUseCaseImpl : AddItemUseCase {

    override operator fun invoke(id: Int, quantity: Double): Boolean {
        val item = availableItems.find { it.id == id }
        val itemOrder = currentCart.find { it.item.id == id }
        val index = currentCart.indexOf(itemOrder)

        val newItemOrder = item?.let {
            val newQuantity = quantity.plus(itemOrder?.quantity ?: 0.0)
            ItemOrder(
                item = it,
                quantity = newQuantity,
                totalPrice = newQuantity * it.price
            )
        }

        return if (itemOrder != null) {
            currentCart.remove(itemOrder)
            if (newItemOrder != null) {
                currentCart.add(index, newItemOrder)
                true
            } else {
                false
            }
        } else {
            newItemOrder?.let { currentCart.add(it) } ?: false
        }
    }

}
