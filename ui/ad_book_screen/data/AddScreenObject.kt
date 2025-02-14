package com.almazingoff.bookstoreapp.ui.add_book_screen.data

import kotlinx.serialization.Serializable

@Serializable
data class AddScreenObject(
    val key: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = ""
)