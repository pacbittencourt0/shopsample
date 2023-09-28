package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.currentCart
import com.pacbittencourt.shop.domain.model.Item
import com.pacbittencourt.shop.domain.model.ItemOrder
import org.junit.Test

class AddItemUseCaseTest {

    @Test
    fun `when add item to empty cart should be the only item in cart`() {
        currentCart.clear()

        val useCase = AddItemUseCaseImpl()
        val quantity = 2.0

        val result = useCase(1, quantity)

        assert(result)
        assert(currentCart.size == 1)
        assert(currentCart[0].quantity == quantity)
    }

    @Test
    fun `when add different item to not empty cart should increase cart count`() {
        currentCart.clear()
        val itemOrder = ItemOrder(item = availableItems[2], 3.0, 1.0)
        currentCart.add(itemOrder)

        val useCase = AddItemUseCaseImpl()
        val quantity = 2.0

        val result = useCase(1, quantity)

        assert(result)
        assert(currentCart.size == 2)
        assert(currentCart[1].quantity == quantity)
    }

    @Test
    fun `when add item that already exists should sum with the existing`() {
        currentCart.clear()
        val itemOrder = ItemOrder(item = availableItems[7], 1.0, 70.0)
        currentCart.add(itemOrder)

        val useCase = AddItemUseCaseImpl()
        val quantity = 1.0

        val result = useCase(7, quantity)

        assert(result)
        assert(currentCart.size == 1)
        assert(currentCart[0].quantity == 2.0)
        assert(currentCart[0].totalPrice == 140.0)
    }

    @Test
    fun `when add nonexistent item should not add to cart`() {
        currentCart.clear()
        val itemOrder = ItemOrder(item = Item(999, "", 1.0), 1.0, 70.0)
        currentCart.add(itemOrder)

        val useCase = AddItemUseCaseImpl()
        val quantity = 1.0

        val result = useCase(999, quantity)

        assert(!result)
        assert(currentCart.size == 0)
    }

    @Test
    fun `when add item with double quantity item should calculate price correctly`() {
        currentCart.clear()

        val useCase = AddItemUseCaseImpl()
        val quantity = 1.5

        val result = useCase(9, quantity)

        assert(result)
        assert(currentCart.size == 1)
        assert(currentCart[0].totalPrice == 15.0)
    }
}