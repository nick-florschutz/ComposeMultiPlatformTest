import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import features.entries_list.ui.EntriesListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {

    KoinApplication(
        application = {
            modules(appModules())
        }
    ) {
        MaterialTheme {
            Navigator(
                screen = EntriesListScreen()
            )
        }
    }
}