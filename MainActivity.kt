package com.almazingoff.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.almazingoff.bookstoreapp.ui.add_book_screen.AddBookScreen
import com.almazingoff.bookstoreapp.ui.add_book_screen.data.AddScreenObject
import com.almazingoff.bookstoreapp.ui.details_screen.data.DetailsNavObject
import com.almazingoff.bookstoreapp.ui.details_screen.ui.DetailsScreen
import com.almazingoff.bookstoreapp.ui.login.LoginScreen
import com.almazingoff.bookstoreapp.ui.login.data.LoginScreenObject
import com.almazingoff.bookstoreapp.ui.login.data.MainScreenDataObject
import com.almazingoff.bookstoreapp.ui.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = LoginScreenObject) {
                composable<LoginScreenObject> {
                    LoginScreen { navData ->
                        navController.navigate(navData)
                    }
                }
                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen(
                        navData,
                        onBookClick = { bk ->
                            navController.navigate(
                                DetailsNavObject(
                                title = bk.title,
                                description = bk.description,
                                price = bk.price,
                                category = bk.category,
                                imageUrl = bk.imageUrl
                            )
                            )
                        },
                        onBookEditClick = { book ->
                            navController.navigate(
                                AddScreenObject(
                                key = book.key,
                                title = book.title,
                                description = book.description,
                                price = book.price,
                                category = book.category,
                                imageUrl = book.imageUrl
                            )
                            )
                        }
                    ) {
                        navController.navigate(AddScreenObject())
                    }
                }
                composable<AddScreenObject> { navEntry ->
                    val navData = navEntry.toRoute<AddScreenObject>()
                    AddBookScreen(navData) {
                        navController.popBackStack()
                    }
                }
                composable<DetailsNavObject> {navEntry ->
                    val navData = navEntry.toRoute<DetailsNavObject>()
                    DetailsScreen(navData)
                }
            }
        }
    }
}
