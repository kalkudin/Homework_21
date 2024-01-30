package com.example.homework_21.data.repository

import com.example.homework_21.data.common.HandleResponse
import com.example.homework_21.data.common.Resource
import com.example.homework_21.data.local.dao.ShopItemDao
import com.example.homework_21.data.remote.mapper.asResource
import com.example.homework_21.data.remote.mapper.mapResource
import com.example.homework_21.data.remote.mapper.toDomain
import com.example.homework_21.data.remote.mapper.toEntity
import com.example.homework_21.data.remote.service.ShopItemService
import com.example.homework_21.data.util.NetworkStatusTracker
import com.example.homework_21.domain.model.ShopItem
import com.example.homework_21.domain.repository.ShopItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShopItemRepositoryImpl @Inject constructor(
    private val shopItemService: ShopItemService,
    private val handleResponse: HandleResponse,
    private val networkStatusTracker: NetworkStatusTracker,
    private val shopItemDao: ShopItemDao
) : ShopItemRepository {

    override suspend fun getShopItems(): Flow<Resource<List<ShopItem>>> {
        return if(networkStatusTracker.isConnected()) {
            fetchFromNetwork()
        }
        else fetchFromDatabase()
    }

    private suspend fun fetchFromNetwork(): Flow<Resource<List<ShopItem>>> {
        return handleResponse.handleApiCall { shopItemService.getShopItems() }
            .mapResource { it.map { dto -> dto.toDomain() } }
            .asResource { shopItems ->
                withContext(Dispatchers.IO) {
                    shopItems.forEach { shopItem ->
                        shopItemDao.insert(shopItem.toEntity())
                    }
                }
                shopItems
            }
    }

    private fun fetchFromDatabase(): Flow<Resource<List<ShopItem>>> = flow {
        emit(Resource.Loading)
        try {
            shopItemDao.getAllItems().collect { entities ->
                if (entities.isEmpty()) {
                    emit(Resource.Error("No Items Found"))
                } else {
                    val shopItems = entities.map { it.toDomain() }
                    emit(Resource.Success(shopItems))
                }
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch data from the database: ${e.localizedMessage}"))
        }
    }
}