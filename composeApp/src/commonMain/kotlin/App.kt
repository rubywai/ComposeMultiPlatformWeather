
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import view.HomeScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        MaterialTheme(
            colors = lightColors(
                primary = Color(0xff283593)
            )
        ) {
            Navigator(screen = HomeScreen())
        }
    }
}


