package com.pacbittencourt.shop.domain.model

data class ItemOrder(
    val item: Item,
    val quantity: Double,
    val totalPrice: Double,
)
