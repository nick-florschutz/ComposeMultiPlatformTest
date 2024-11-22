import di.buildEntriesListModule
import di.buildFavoritesListScreenModule


fun appModules(context: Any? = null) = listOf(
    buildEntriesListModule(context),
    buildFavoritesListScreenModule(context)
)