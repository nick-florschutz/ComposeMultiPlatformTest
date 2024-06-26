package features.entries_list.domain

import data_sources.local.PeopleDatabase
import data_sources.preferences.DataStoreRepository
import data_sources.preferences.createDataStore
import features.entries_list.presentation.EntriesListScreenModel
import org.koin.dsl.module


fun buildEntriesListModule(context: Any? = null) = module {
    single { DataStoreRepository(createDataStore(context = context)) }
    single { get<PeopleDatabase>().peopleDao() }
    factory { EntriesListScreenModel(get(), get()) }
}