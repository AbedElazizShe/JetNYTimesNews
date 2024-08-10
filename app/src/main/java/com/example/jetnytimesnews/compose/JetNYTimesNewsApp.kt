package com.example.jetnytimesnews.compose

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetnytimesnews.compose.home.HomeScreen
import com.example.jetnytimesnews.compose.savedforlater.SavedForLaterScreen

@Composable
fun JetNYTimesNewsApp() {
    val navController = rememberNavController()
    JetNYTimesNewsNavHost(navController)
}

@Composable
private fun JetNYTimesNewsNavHost(navHostController: NavHostController) {

    val activity = (LocalContext.current as Activity)
    NavHost(navController = navHostController, startDestination = Screen.Home.route) {

        composable(route = Screen.Home.route) {
            HomeScreen(
                savedClick = {
                    navHostController.navigate(Screen.SavedForLater.route)
                },
                onShareClick = {
                    createShareIntent(activity, it)
                }
            ) {
                val uri = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                activity.startActivity(intent)
            }
        }
        composable(route = Screen.SavedForLater.route) {
            SavedForLaterScreen(
                onUpClick = {
                    navHostController.navigateUp()
                },
                onShareClick = {
                    createShareIntent(activity, it)
                }
            ) {
                val uri = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                activity.startActivity(intent)
            }
        }
    }
}

private fun createShareIntent(activity: Activity, url: String) {
    val shareIntent = ShareCompat.IntentBuilder(activity)
        .setText(url)
        .setType("text/plain")
        .createChooserIntent()
        .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
    activity.startActivity(shareIntent)
}
