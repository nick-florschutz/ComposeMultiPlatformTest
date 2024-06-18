import features.entries_list.domain.buildEntriesListModule


fun appModules(context: Any? = null) = listOf(
    buildEntriesListModule(context),
)