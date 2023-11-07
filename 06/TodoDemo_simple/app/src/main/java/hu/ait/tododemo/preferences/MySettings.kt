package hu.ait.tododemo.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MySettings(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("mysettings")
        private val FIRST_START = booleanPreferencesKey("firstStart")
    }

    val isFirstStart: Flow<Boolean> = context.dataStore.data.map {
            preferences ->
        preferences[FIRST_START] ?: true
    }

    suspend fun saveFirstStart(firstStart: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[FIRST_START] = firstStart
        }
    }
}