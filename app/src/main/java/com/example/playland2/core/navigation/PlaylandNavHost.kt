package com.example.playland2.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playland2.feature.menu.MenuScreen
import com.example.playland2.feature.snake.ui.SnakeScreen
import com.example.playland2.feature.flappybird.FlappyBirdScreen
import com.example.playland2.feature.tictactoe.ui.TicTacToeScreen

import androidx.lifecycle.Lifecycle
import com.example.playland2.feature.catchfood.ui.navigation.CatchFoodNavHost

@Composable
fun PlaylandNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainMenu,
        modifier = modifier
    ) {
        composable<MainMenu> {
            MenuScreen(
                onNavigateToSnake = { 
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process { navController.navigate(SnakeGame) }
                    }
                },
                onNavigateToCatchFood = { 
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process { navController.navigate(CatchFood) }
                    }
                },
                onNavigateToFlappyBird = { 
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process { navController.navigate(FlappyBird) }
                    }
                },
                onNavigateToTicTacToe = { 
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process { navController.navigate(TicTacToe) }
                    }
                }
            )
        }
        composable<SnakeGame> { 
            SnakeScreen(onBack = { 
                if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                    NavDebouncer.process { navController.popBackStack() } 
                }
            }) 
        }
        composable<CatchFood> {

            CatchFoodNavHost(

                onBack = {

                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {

                        NavDebouncer.process {

                            navController.popBackStack()
                        }
                    }
                }
            )
        }
        composable<FlappyBird> { 
            FlappyBirdScreen(onBack = { 
                if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                    NavDebouncer.process { navController.popBackStack() } 
                }
            }) 
        }
        composable<TicTacToe> { 
            TicTacToeScreen(onBack = { 
                if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                    NavDebouncer.process { navController.popBackStack() } 
                }
            })
        }
    }
}
