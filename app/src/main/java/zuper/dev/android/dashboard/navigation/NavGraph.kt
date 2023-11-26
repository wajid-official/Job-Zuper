package zuper.dev.android.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import zuper.dev.android.dashboard.data.model.JobModel
import zuper.dev.android.dashboard.view.DashboardPage
import zuper.dev.android.dashboard.view.JobPage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun CreateNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.DASHBOARD) {
        composable(Screens.DASHBOARD) {
            DashboardPage(navController)
        }
        composable(
            Screens.JOB,
            listOf(navArgument(Arguments.JOB_DATA) { type = NavType.StringType })
        ) {
            val job = Gson().fromJson(
                URLDecoder.decode(
                    it.arguments?.getString(Arguments.JOB_DATA), StandardCharsets.UTF_8.name()
                ),
                JobModel::class.java
            )
            JobPage(
                navController,
                job
            )
        }
    }
}