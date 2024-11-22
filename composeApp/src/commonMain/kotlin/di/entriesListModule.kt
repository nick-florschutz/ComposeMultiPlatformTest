package di

import backend_features.firebase_authentication.FirebaseAuthManager
import com.plusmobileapps.konnectivity.Konnectivity
import data_sources.local.PeopleDao
import data_sources.local.PeopleDatabase
import data_sources.preferences.DataStoreRepository
import data_sources.preferences.createDataStore
import features.entries_list.presentation.EntriesListScreenModel
import org.koin.dsl.bind
import org.koin.dsl.module


fun buildEntriesListModule(context: Any? = null) = module {
    single { DataStoreRepository(createDataStore(context = context)) }
    single { get<PeopleDatabase>().peopleDao() }.bind(PeopleDao::class)
    single { Konnectivity() }
    single { FirebaseAuthManager }
    single { EntriesListScreenModel(get(), get(), get(), get()) }
}