package com.almazingoff.bookstoreapp.ui.details_screen.data

import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavObject(
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = ""
)
