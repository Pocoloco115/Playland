package com.example.playland2.feature.catchfood.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playland2.feature.catchfood.CatchFoodScreen
import com.example.playland2.feature.catchfood.ui.screen.CatchFoodGame

@Composable
fun CatchFoodNavHost(
    onBack: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CatchFoodMenu
    ) {

        // MENU DE CATCH FOOD
        composable<CatchFoodMenu> {

            CatchFoodScreen(

                onBack = {
                    onBack()
                },

                onGoToGame = {

                    navController.navigate(CatchFoodGameScreen)
                }
            )
        }

        // GAMEPLAY
        composable<CatchFoodGameScreen> {

            CatchFoodGame(

                onBack = {

                    navController.popBackStack()
                }
            )
        }
    }
}