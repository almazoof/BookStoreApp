package com.almazingoff.bookstoreapp.ui.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.almazingoff.bookstoreapp.ui.theme.ButtonColor

@Composable
fun LoginButton(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = {
        onClick()
    },
        modifier = Modifier.fillMaxWidth(0.5f),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor
        )
    ) {
        Text(text = text)
    }
}