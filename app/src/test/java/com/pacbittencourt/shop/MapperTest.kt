package com.pacbittencourt.shop

import com.pacbittencourt.shop.domain.model.Item
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order
import org.junit.Test

class MapperTest {

    private val id = 1
    private val description = "desc"
    private val price = 1.0
    private val item = Item(id, description, price)
    private val itemOrder = ItemOrder(
        item = item,
        quantity = 2.0,
        totalPrice = 2.0
    )

    @Test
    fun `should map item order to ui`() {
        val itemOrderUi = itemOrder.mapToItemOrderUi()

        assert(itemOrderUi.itemDescription == description)
        assert(itemOrderUi.itemPrice == "1,00")
        assert(itemOrderUi.quantity == "2")
        assert(itemOrderUi.totalPrice == "2,00")
    }

    @Test
    fun `should map order to ui`() {
        val order = Order(
            items = listOf(itemOrder),
            orderPrice = 2.0
        )

        val orderUi = order.mapToOrderDetailsUi()

        assert(orderUi.items.size == 1)
        assert(orderUi.totalPrice == "2,00")
    }

    @Test
    fun `should map order to order summary ui`() {
        val order = Order(
            items = listOf(itemOrder),
            orderPrice = 2.0
        )

        val orderUi = order.mapToOrderSummaryUi("1")

        assert(orderUi.orderId == "1")
        assert(orderUi.totalPrice == "2,00")
        assert(orderUi.itemQuantity == "1")
    }
}