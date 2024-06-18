import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(appModules())
    }
}