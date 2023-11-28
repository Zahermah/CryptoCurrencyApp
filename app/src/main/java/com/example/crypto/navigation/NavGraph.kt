package com.example.crypto.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.crypto.screens.detail.DetailScreen
import com.example.crypto.screens.first.FirstScreen
import com.example.crypto.screens.home.CurrencyListScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.First.route
    ) {
        composable(Screens.First.route) {
            FirstScreen(navController)
        }
        composable(Screens.Home.route) {
            CurrencyListScreen(navController)
        }
        composable(
            route = "Detail_screen/{priceUsd}/{name}/{id}",
            arguments = listOf(
                navArgument("priceUsd") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType },
                navArgument("id") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val priceUsd = backStackEntry.arguments?.getString("priceUsd")
            val name = backStackEntry.arguments?.getString("name")
            val id = backStackEntry.arguments?.getString("id")
            if (priceUsd != null && name != null && id != null) {
                DetailScreen(navController, priceUsd, name, id)
            } else {
               Log.i("Navgraph","Cant find destination")
            }
        }

    }
}

