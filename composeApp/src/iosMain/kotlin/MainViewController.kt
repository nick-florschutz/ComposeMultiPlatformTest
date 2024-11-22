
import androidx.compose.ui.window.ComposeUIViewController
import data_sources.local.getDatabaseBuilder
import data_sources.local.getRoomDatabase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    val iosModule = module {
        single { getRoomDatabase(getDatabaseBuilder()) }
    }

    Napier.base(DebugAntilog())

    startKoin {
        modules(appModules() + iosModule)
    }
}