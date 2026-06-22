package com.example.playland2.feature.catchfood.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playland2.feature.catchfood.CatchFoodScreen
import com.example.playland2.feature.catchfood.ui.screen.CatchFoodGame
import com.example.playland2.feature.catchfood.ui.screen.CatchFoodLeaderboard

@Composable
fun CatchFoodNavHost(
    onBack: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CatchFoodMenu
    ) {

        // menu principal el del historico
        composable<CatchFoodMenu> {

            CatchFoodScreen(

                onBack = {

                    onBack()
                },

                onGoToGame = {

                    navController.navigate(
                        CatchFoodGameScreen
                    )
                },

                onGoToLeaderboard = {
                    navController.navigate(CatchFoodLeaderboardScreen)
                }
            )
        }

        // jueguito
        composable<CatchFoodGameScreen> {

            CatchFoodGame(

                onBack = {

                    navController.popBackStack()
                }
            )
        }

        composable<CatchFoodLeaderboardScreen> {
            CatchFoodLeaderboard(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
