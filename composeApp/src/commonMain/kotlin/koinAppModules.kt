import di.buildEntriesListModule


fun appModules(context: Any? = null) = listOf(
    buildEntriesListModule(context),
)