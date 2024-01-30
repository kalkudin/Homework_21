package com.example.homework_21.data.remote.mapper

import com.example.homework_21.data.local.entity.ShopItemEntity
import com.example.homework_21.domain.model.ShopItem

fun ShopItem.toEntity() : ShopItemEntity {
    return ShopItemEntity(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite
    )
}