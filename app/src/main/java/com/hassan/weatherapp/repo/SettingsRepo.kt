package com.hassan.weatherapp.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepo @Inject constructor(
    @param:ApplicationContext val context: Context,
) {
    private val TEMP_UNIT = stringPreferencesKey("temp_unit")

    val tempUnitFlow = context.dataStore.data.map {
        it[TEMP_UNIT]
    }

    suspend fun toggleTempUnit() {
        context.dataStore.edit {
            it[TEMP_UNIT] = if (it[TEMP_UNIT] == "F") "C" else "F"
        }
    }
}