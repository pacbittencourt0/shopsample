package com.pacbittencourt.shop.ui.model

data class OrderDetailsUi(
    val items: List<ItemOrderUi>,
    val totalPrice: String,
)