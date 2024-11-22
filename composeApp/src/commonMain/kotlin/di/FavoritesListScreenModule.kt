package di

import features.favorites_list.presentation.FavoritesListScreenScreenModel
import org.koin.dsl.module

fun buildFavoritesListScreenModule(context: Any? = null) = module {
    single { FavoritesListScreenScreenModel() }
}