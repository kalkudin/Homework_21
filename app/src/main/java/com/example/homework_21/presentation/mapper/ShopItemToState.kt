package com.example.homework_21.presentation.mapper

import com.example.homework_21.domain.model.ShopItem
import com.example.homework_21.presentation.model.Item

fun shopItemToState(shopItem: ShopItem): Item {
    return Item(
        id = shopItem.id,
        image = shopItem.cover,
        price = shopItem.price,
        title = shopItem.title,
        favorite = if (shopItem.favorite) Item.Favorite.FAVORITE else Item.Favorite.NOT_FAVORITE
    )
}