package com.pacbittencourt.shop

import com.pacbittencourt.shop.domain.model.Item
import com.pacbittencourt.shop.domain.model.ItemOrder
import com.pacbittencourt.shop.domain.model.Order

val availableItems = listOf(
    Item(9, "Castanha de Caju", 10.0),
    Item(1, "Celular 4G", 799.0),
    Item(2, "Celular 5G", 999.0),
    Item(3, "Capinha", 19.0),
    Item(4, "Fone de Ouvido Bluetooth", 189.90),
    Item(5, "Teclado", 89.50),
    Item(6, "Mouse", 39.90),
    Item(7, "Carregador", 70.0),
    Item(8, "Mouse Pad", 122.0),
)

val currentCart = mutableListOf<ItemOrder>()

val orders = mutableListOf<Order>()