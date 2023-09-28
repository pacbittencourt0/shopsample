package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.ItemOrder
import org.junit.Test

class ClearCartUseCaseTest {

    @Test
    fun `when clear a empty cart should continue empty`() {
        currentCart.clear()

        val useCase = ClearCartUseCaseImpl()
        useCase()

        assert(currentCart.size == 0)
    }

    @Test
    fun `when clear a filled cart should be empty`() {
        currentCart.clear()
        val itemOrder = ItemOrder(item = availableItems[0], 3.0, 1.0)
        currentCart.add(itemOrder)

        val useCase = ClearCartUseCaseImpl()
        useCase()

        assert(currentCart.size == 0)
    }
}