package com.example.homework_21.di

import com.example.homework_21.data.common.HandleResponse
import com.example.homework_21.data.local.dao.ShopItemDao
import com.example.homework_21.data.remote.service.ShopItemService
import com.example.homework_21.data.repository.ShopItemRepositoryImpl
import com.example.homework_21.data.util.NetworkStatusTracker
import com.example.homework_21.domain.repository.ShopItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideShopItemRepository(
        shopItemService: ShopItemService,
        handleResponse: HandleResponse,
        networkStatusTracker: NetworkStatusTracker,
        shopItemDao: ShopItemDao
    ): ShopItemRepository {
        return ShopItemRepositoryImpl(
            shopItemService = shopItemService,
            handleResponse = handleResponse,
            networkStatusTracker = networkStatusTracker,
            shopItemDao = shopItemDao
        )
    }
}