package uk.ac.tees.mad.quickdish.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.quickdish.DishViewModel
import uk.ac.tees.mad.quickdish.ui.screens.HomeScreen
import uk.ac.tees.mad.quickdish.ui.screens.RecipeResultsScreen
import uk.ac.tees.mad.quickdish.ui.screens.SplashScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object RecipeResults : Screen("recipe_results")
    data object RecipeDetails : Screen("recipe_details")
    data object Settings : Screen("settings")
}


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val viewModel : DishViewModel = viewModel()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {


        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            // Implement HomeScreen
            HomeScreen (viewModel, navController)
        }

        composable(Screen.RecipeResults.route) {
            RecipeResultsScreen(viewModel, onBackClick = {navController.popBackStack()})
        }

        composable(Screen.RecipeDetails.route) {
            // Implement RecipeDetailsScreen
        }

        composable(Screen.Settings.route) {
            // Implement SettingsScreen
        }
    }
}