package com.almazingoff.bookstoreapp.ui.add_book_screen

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.almazingoff.bookstoreapp.R
import com.almazingoff.bookstoreapp.data.Book
import com.almazingoff.bookstoreapp.ui.add_book_screen.data.AddScreenObject
import com.almazingoff.bookstoreapp.ui.login.LoginButton
import com.almazingoff.bookstoreapp.ui.login.RoundedCornerTextField
import com.almazingoff.bookstoreapp.ui.theme.BoxFilterColor
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lmazingoff.bookstoreapp.ui.add_book_screen.RoundedCornerDropDownMenu

@Preview(showBackground = true)
@Composable
fun AddBookScreen(
    navData: AddScreenObject = AddScreenObject(),
    onSaved: () -> Unit = {},
    onError: () -> Unit = {}
) {
    val cv = LocalContext.current.contentResolver
    
    var  selectedCategory = remember {
        mutableStateOf(navData.category)
    }
    val title = remember {
        mutableStateOf(navData.title)
    }
    val description = remember {
        mutableStateOf(navData.description)
    }
    val price = remember {
        mutableStateOf(navData.price)
    }
    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val imageBitmap = remember {
        var bitmap: Bitmap? = null
        try {
            val base64Image = Base64.decode(navData.imageUrl, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(
                base64Image, 0,
                base64Image.size
            )
        } catch (e: IllegalArgumentException) {

        }
        mutableStateOf(bitmap)
    }
    val firestore = remember {
        Firebase.firestore
    }
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageBitmap.value = null
        selectedImageUri.value = uri
    }

    Image(
        painter = rememberAsyncImagePainter(
        model = imageBitmap.value ?: selectedImageUri.value,
        ),
        contentDescription = "BG",
        modifier= Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.4f
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BoxFilterColor)
    ){

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 50.dp, end = 50.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp))
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Add new book",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(15.dp))
        RoundedCornerDropDownMenu(selectedCategory.value) { selectedItem ->
                selectedCategory.value = selectedItem

        }
        Spacer(modifier = Modifier.height(5.dp))

        RoundedCornerTextField(
            text = title.value,
            label = "Title"
        ) {
            title.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            maxLines = 5,
            singleLine = false,
            text = description.value,
            label = "Description"
        ) {
            description.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = price.value,
            label = "Price"
        ) {
            price.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "Select image") {
            imageLauncher.launch("image/*")
        }
        LoginButton(text = "Save") {
            saveBookToFireStore(
                firestore,
                Book(
                    key = navData.key,
                    title = title.value,
                    description = description.value,
                    price = price.value,
                    category = selectedCategory.value,
                    imageUrl = if (selectedImageUri.value != null)
                        imageToBase64(
                        selectedImageUri.value!!,
                        cv
                    )
                    else navData.imageUrl
                ),
                onSaved = {
                    onSaved()
                },
                onError = {
                    onError()
                }
            )
        }
    }
}

private fun imageToBase64(uri: Uri, contentResolver: ContentResolver): String {
    val inputStream = contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes()
    return bytes?.let {
        Base64.encodeToString(it, Base64.DEFAULT)
    } ?: ""
}

private fun saveBookToFireStore(
    firestore: FirebaseFirestore,
    book: Book,
    onSaved: () -> Unit,
    onError: () -> Unit
) {
    val db = firestore.collection("books")
    val key = book.key.ifEmpty { db.document().id }
    db.document(key)
        .set(
            book.copy(key = key)
        )
        .addOnSuccessListener {
            onSaved()
        }
        .addOnFailureListener {
            onError()
        }
}