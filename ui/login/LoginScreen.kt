package com.almazingoff.bookstoreapp.ui.login

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.almazingoff.bookstoreapp.R
import com.almazingoff.bookstoreapp.ui.login.data.MainScreenDataObject
import com.almazingoff.bookstoreapp.ui.theme.BoxFilterColor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun LoginScreen(
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit
) {
    val auth = remember {
        Firebase.auth
    }
    val errorState = remember {
        mutableStateOf("")
    }

    val emailState = remember {
        mutableStateOf("sandr4@yandex.ru")
    }
    val passwordState = remember {
        mutableStateOf("123456789")
    }

    Image(
        painter = painterResource(id = R.drawable.book_store_bg),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Box(
        modifier = Modifier.fillMaxSize()
            .background(BoxFilterColor)
    ){

    }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(start = 50.dp, end = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.books),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp))
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Book Store",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = FontFamily.Serif
            )
        Spacer(modifier = Modifier.height(5.dp))

       RoundedCornerTextField(
           text = emailState.value,
            label = "Email"
       ) {
           emailState.value = it
       }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = passwordState.value,
            label = "Password"
        ) {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
        LoginButton(text = "Sign In") {
            signIn(auth,
                emailState.value,
                passwordState.value,
                onSignInSuccess = { navData ->
                    onNavigateToMainScreen(navData)
                },
                onSignInFailure = { error ->
                    errorState.value = error
                }
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LoginButton(text = "Sign Up") {
            signUp(
                auth,
                emailState.value,
                passwordState.value,
                onSignUpSuccess = { navData ->
                    onNavigateToMainScreen(navData)
                },
                onSignUpFailure = { error ->
                    errorState.value = error
                }
            )
        }
    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSuccess: (MainScreenDataObject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    if(email.isBlank() || password.isBlank()) {
        onSignUpFailure("Email or Password cannot be empty")
        return
    }
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignUpSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Sign Up Error")
        }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSuccess: (MainScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
) {
    if(email.isBlank() ||password.isBlank()) {
        onSignInFailure("Email or Password cannot be empty")
        return
    }
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSignInSuccess(
                    MainScreenDataObject(
                        task.result.user?.uid!!,
                        task.result.user?.email!!
                    )
                )
            }
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Sign In Error")
        }
}


