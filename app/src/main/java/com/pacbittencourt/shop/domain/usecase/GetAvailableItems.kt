package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface GetAvailableItems {
    operator fun invoke(): Flow<List<Item>>
}