package com.pacbittencourt.shop.domain.model

data class Order(
    val items: List<ItemOrder>,
    val orderPrice: Double
)
