package features.favorites_list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import features.favorites_list.presentation.FavoritesListScreenScreenModel

class FavoritesListScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val screenModel = navigator.koinNavigatorScreenModel<FavoritesListScreenScreenModel>()

        val tilesList = screenModel.tilesList.toList()

        LazyVerticalGrid(
            columns = GridCells.Adaptive(125.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(tilesList) { tile ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(125.dp)
                        .clickable {
                            // TODO
                        }
                ) {
                    Text(
                        text = tile,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        }

    }
}