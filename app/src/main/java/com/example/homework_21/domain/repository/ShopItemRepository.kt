package com.example.homework_21.domain.repository

import com.example.homework_21.data.common.Resource
import com.example.homework_21.domain.model.ShopItem
import kotlinx.coroutines.flow.Flow

interface ShopItemRepository {
    suspend fun getShopItems() : Flow<Resource<List<ShopItem>>>
}