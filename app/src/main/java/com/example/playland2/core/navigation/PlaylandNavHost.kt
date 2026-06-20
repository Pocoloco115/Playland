package com.example.playland2.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playland2.feature.catchfood.CatchFoodScreen
import com.example.playland2.feature.flappybird.FlappyBirdScreen
import com.example.playland2.feature.menu.MenuScreen
import com.example.playland2.feature.snake.SnakeScreen
import com.example.playland2.feature.tictactoe.TicTacToeScreen

private const val ROUTE_MENU = "menu"
private const val ROUTE_SNAKE = "snake"
private const val ROUTE_FLAPPY = "flappy"
private const val ROUTE_CATCH_FOOD = "catch_food"
private const val ROUTE_TIC_TAC_TOE = "tic_tac_toe"

@Composable
fun PlaylandNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ROUTE_MENU,
        modifier = modifier
    ) {
        composable(ROUTE_MENU) {
            MenuScreen(
                onNavigateToSnake = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.navigate(ROUTE_SNAKE)
                        }
                    }
                },
                onNavigateToTicTacToe = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.navigate(ROUTE_TIC_TAC_TOE)
                        }
                    }
                },
                onNavigateToCatchFood = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.navigate(ROUTE_CATCH_FOOD)
                        }
                    }
                },
                onNavigateToFlappyBird = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.navigate(ROUTE_FLAPPY)
                        }
                    }
                }
            )
        }

        composable(ROUTE_SNAKE) {
            SnakeScreen(
                onBack = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable(ROUTE_CATCH_FOOD) {
            CatchFoodScreen(
                onBack = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable(ROUTE_FLAPPY) {
            FlappyBirdScreen(
                onBack = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }

        composable(ROUTE_TIC_TAC_TOE) {
            TicTacToeScreen(
                onBack = {
                    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
                        NavDebouncer.process {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    }
}