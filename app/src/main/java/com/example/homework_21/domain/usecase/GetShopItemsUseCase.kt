package com.example.homework_21.domain.usecase

import android.util.Log
import com.example.homework_21.data.common.Resource
import com.example.homework_21.domain.model.ShopItem
import com.example.homework_21.domain.repository.ShopItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShopItemsUseCase @Inject constructor(private val shopItemRepository: ShopItemRepository) {
    suspend operator fun invoke() : Flow<Resource<List<ShopItem>>> {
        return shopItemRepository.getShopItems()
    }
}