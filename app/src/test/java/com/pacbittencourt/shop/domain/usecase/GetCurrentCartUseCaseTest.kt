package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.ItemOrder
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetCurrentCartUseCaseTest {

    @Test
    fun `should return empty list when cart is empty`() = runTest {
        currentCart.clear()

        val useCase = GetCurrentCartUseCaseImpl()

        val result = useCase()
        result.collect {
            assert(it.isEmpty())
        }
    }

    @Test
    fun `should return list when cart is not empty`() = runTest {
        currentCart.clear()
        val itemOrder = ItemOrder(item = availableItems[1], 3.0, 1.0)
        currentCart.add(itemOrder)

        val useCase = GetCurrentCartUseCaseImpl()

        val result = useCase()
        result.collect {
            assert(it.isNotEmpty())
        }
    }
}