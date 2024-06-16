package features.entries_list.domain

import features.entries_list.presentation.EntriesListScreenModel
import org.koin.dsl.module


val entriesListModule = module {
    factory { EntriesListScreenModel() }
}