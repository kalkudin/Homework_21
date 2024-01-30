package com.example.homework_21.data.remote.service

import com.example.homework_21.data.remote.model.ShopItemDto
import retrofit2.Response
import retrofit2.http.GET

interface ShopItemService {
    @GET("/v3/1775d634-92dc-4c32-ae71-1707b8cfee41")
    suspend fun getShopItems() : Response<List<ShopItemDto>>
}