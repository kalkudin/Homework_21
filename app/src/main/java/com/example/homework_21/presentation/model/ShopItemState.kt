package com.example.homework_21.presentation.model

data class ShopItemState(
    val isLoading : Boolean = false,
    val isSuccess : List<Item>? = null,
    val isError : String? = null
)