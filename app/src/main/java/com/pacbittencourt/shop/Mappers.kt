package com.pacbittencourt.shop

import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order
import com.pacbittencourt.shop.ui.model.ItemOrderUi
import com.pacbittencourt.shop.ui.model.OrderDetailsUi
import com.pacbittencourt.shop.ui.model.OrderSummaryUi

fun ItemOrder.mapToItemOrderUi() =
    ItemOrderUi(
        itemPrice = item.price.formatCurrency(),
        itemDescription = item.description,
        quantity = quantity.toString(),
        totalPrice = totalPrice.formatCurrency()
    )

fun Order.mapToOrderDetailsUi() =
    OrderDetailsUi(
        items = items.map {
            it.mapToItemOrderUi()
        },
        totalPrice = orderPrice.formatCurrency()
    )

fun Order.mapToOrderSummaryUi(id: String) =
    OrderSummaryUi(
        orderId = id,
        itemQuantity = items.size.toString(),
        totalPrice = orderPrice.formatCurrency()
    )
