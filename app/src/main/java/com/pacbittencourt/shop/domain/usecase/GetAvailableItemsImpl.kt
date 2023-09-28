package com.pacbittencourt.shop.domain.usecase

import com.pacbittencourt.shop.availableItems
import com.pacbittencourt.shop.domain.model.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAvailableItemsImpl : GetAvailableItems {

    override operator fun invoke(): Flow<List<Item>> {
        return flow {
            delay(1000L)
            emit(availableItems)
        }
    }
}