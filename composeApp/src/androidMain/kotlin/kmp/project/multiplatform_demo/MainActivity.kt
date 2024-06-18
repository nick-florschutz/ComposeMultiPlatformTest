package kmp.project.multiplatform_demo

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import appModules
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            modules(appModules(context = this@MainActivity))
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