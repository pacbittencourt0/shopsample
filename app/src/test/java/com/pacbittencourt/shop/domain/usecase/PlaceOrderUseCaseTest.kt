package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.orders
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PlaceOrderUseCaseTest {
    @Test
    fun `should get the correct details from a order`() = runTest {
        orders.clear()
        val itemOrder = ItemOrder(item = availableItems[0], 1.0, 799.0)
        val order = Order(listOf(itemOrder), 799.00)

        val placeOrderUseCase = PlaceOrderUseCaseImpl()
        val result = placeOrderUseCase(order)

        assert(result)
        assert(orders.size == 1)
        assert(orders[0].items.size == 1)
        assert(orders[0].orderPrice == 799.0)
    }
}