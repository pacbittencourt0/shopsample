package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetOrderDetailsUseCaseTest {
    @Test
    fun `should get the correct details from a order`() = runTest {
        val itemOrder = ItemOrder(item = availableItems[0], 1.0, 799.0)
        val placeOrderUseCase = PlaceOrderUseCaseImpl()
        placeOrderUseCase(Order(listOf(itemOrder), 799.00))
        val getOrderDetailsUseCase = GetOrderDetailsUseCaseImpl()

        val result = getOrderDetailsUseCase(1)

        val orderResult = result.first()

        assert(orderResult.items.size == 1)
        assert(orderResult.orderPrice == 799.0)
    }
}