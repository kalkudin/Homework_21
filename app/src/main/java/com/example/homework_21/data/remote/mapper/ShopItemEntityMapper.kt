package com.example.homework_21.data.remote.mapper

import com.example.homework_21.data.local.entity.ShopItemEntity
import com.example.homework_21.domain.model.ShopItem

fun ShopItemEntity.toDomain() : ShopItem {
    return ShopItem(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite
    )
}