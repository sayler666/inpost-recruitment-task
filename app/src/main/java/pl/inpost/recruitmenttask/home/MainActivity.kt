package pl.inpost.recruitmenttask.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.core.theme.InPostTheme
import pl.inpost.recruitmenttask.core.theme.StatusBarColor
import pl.inpost.recruitmenttask.shipments.presentation.composable.NavGraphs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InPostApp()
        }
    }
}

@Composable
fun InPostApp() {
    InPostTheme {
        StatusBarColor()
        val navController = rememberNavController()
        Scaffold { scaffoldPadding ->
            Column(
                modifier = Modifier.padding(scaffoldPadding)
            ) {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navController
                )
            }
        }
    }
}
