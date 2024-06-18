package data_sources.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>,
) {

    fun readStringValue(key: String): Flow<String> {
        return dataStore.data
            .catch { emptyFlow<String>() }
            .map { preferences ->
                preferences[stringPreferencesKey(key)].orEmpty()
            }
    }

    suspend fun saveStringValue(key: String, value: String): Boolean {
        try {
            dataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            println(e)
            println(e.message)
            return false
        }
    }

    suspend fun saveBooleanValue(key: String, value: Boolean): Boolean {
        try {
            dataStore.edit { preferences ->
                preferences[booleanPreferencesKey(key)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            println(e)
            println(e.message)
            return false
        }
    }

    suspend fun saveIntValue(key: String, value: Int): Boolean {
        try {
            dataStore.edit { preferences ->
                preferences[intPreferencesKey(key)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            println(e)
            println(e.message)
            return false
        }
    }

    suspend fun saveLongValue(key: String, value: Long): Boolean {
        try {
            dataStore.edit { preferences ->
                preferences[longPreferencesKey(key)] = value
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            println(e)
            println(e.message)
            return false
        }
    }
}