import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import view.HomeScreen
import viewmodel.theme.ThemeViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        val themeViewModel = koinInject<ThemeViewModel>()
        val isDarkThemeState = themeViewModel.isDarkTheme.collectAsState()
        val color = if (isDarkThemeState.value) darkColors()
        else lightColors(
            primary = Color(0xff283593)
        )
        MaterialTheme(
            colors = color,
        ) {
            Navigator(screen = HomeScreen())
        }
    }
}


