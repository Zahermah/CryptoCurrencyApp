package com.example.crypto.navigation

sealed class Screens(val route: String) {
    object First: Screens("first_screen")
    object Home: Screens("home_screen")
    object Detail: Screens("Detail_screen")

}

