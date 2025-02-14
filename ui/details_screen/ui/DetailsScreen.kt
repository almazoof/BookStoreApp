package com.almazingoff.bookstoreapp.ui.details_screen.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.almazingoff.bookstoreapp.ui.details_screen.data.DetailsNavObject

@Preview(showBackground = true)
@Composable
fun DetailsScreen(
    navObject: DetailsNavObject = DetailsNavObject()
) {

    var bitmap: Bitmap? = null
    try {
        val base64Image = Base64.decode(navObject.imageUrl, Base64.DEFAULT)
        bitmap = BitmapFactory.decodeByteArray(
            base64Image, 0,
            base64Image.size
        )
    } catch (e: IllegalArgumentException) {

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = bitmap ?: navObject.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(190.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.FillHeight
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Категория:",
                        color = Color.Gray
                    )
                    Text(text = navObject.category,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Автор:",
                        color = Color.Gray
                    )
                    Text(text = navObject.title,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Дата печати:",
                        color = Color.Gray
                    )
                    Text(text = "06.02.1954",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Оценка: ",
                        color = Color.Gray
                    )
                    Text(text = "4.8",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Fierce Fairy Tales",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = navObject.description,
                fontSize = 16.sp
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                /*TODO*/
        }) {
            Text(text = "${navObject.price} Buy Now")
        }
   }
}