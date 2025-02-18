package com.almazingoff.bookstoreapp.data

data class Book(
    val key: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val isFavorite: Boolean = false
)
