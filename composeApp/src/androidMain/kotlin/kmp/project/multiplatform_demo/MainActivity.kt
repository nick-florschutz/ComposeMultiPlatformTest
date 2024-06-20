package kmp.project.multiplatform_demo

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import appModules
import data_sources.local.getDatabaseBuilder
import data_sources.local.getRoomDatabase
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val androidModules = module {
            single { getRoomDatabase(getDatabaseBuilder(this@MainActivity)) }
        }

        startKoin {
            modules(appModules(context = this@MainActivity) + androidModules)
        }

        // Initialize Napier Logger
        Napier.base(DebugAntilog())

        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clear Napier Logs
        Napier.takeLogarithm()
        stopKoin()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}