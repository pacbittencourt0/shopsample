package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.orders
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetOrdersUseCaseTest {
    @Test
    fun `should get empty list when user has no orders`() = runTest {
        orders.clear()
        val getOrdersUseCase = GetOrdersUseCaseImpl()

        val result = getOrdersUseCase()

        val orderResult = result.first()

        assert(orderResult.isEmpty())
    }

    @Test
    fun `should get filled list when user has orders`() = runTest {
        orders.clear()
        val itemOrder = ItemOrder(item = availableItems[0], 1.0, 799.0)
        val placeOrderUseCase = PlaceOrderUseCaseImpl()
        placeOrderUseCase(Order(listOf(itemOrder), 799.00))
        val getOrdersUseCase = GetOrdersUseCaseImpl()

        val result = getOrdersUseCase()
        val orderResult = result.first()

        assert(orderResult.size == 1)
    }
}