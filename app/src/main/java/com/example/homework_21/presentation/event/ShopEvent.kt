package com.example.homework_21.presentation.event

sealed class ShopEvent {
    data object GetShopItems : ShopEvent()
}