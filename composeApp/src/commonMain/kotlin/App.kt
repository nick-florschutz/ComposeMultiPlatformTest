
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import features.entries_list.ui.EntriesListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

//    KoinApplication(
//        application = {
//            modules(appModules(context))
//        }
//    ) {
//        MaterialTheme {
//            Navigator(
//                screen = EntriesListScreen()
//            )
//        }
//    }

    MaterialTheme {
        Navigator(
            screen = EntriesListScreen()
        )
    }
}