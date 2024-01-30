package com.example.homework_21.data.remote.mapper

import com.example.homework_21.data.remote.model.ShopItemDto
import com.example.homework_21.domain.model.ShopItem

fun ShopItemDto.toDomain() : ShopItem {
    return ShopItem(
        id = id,
        cover = cover,
        price = price,
        title = title,
        favorite = favorite
    )
}