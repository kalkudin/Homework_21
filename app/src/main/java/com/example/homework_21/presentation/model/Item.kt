package com.example.homework_21.presentation.model

data class Item(
    val id : Int,
    val image : String,
    val price : String,
    val title : String,
    val favorite : Favorite
) {
    enum class Favorite {
        FAVORITE,
        NOT_FAVORITE
    }
}
