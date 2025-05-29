package com.example.cotutalk_program.AcessoAPI.viewmodel

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

object UserPreferences {
    private val USER_ID = intPreferencesKey("user_id")

    suspend fun salvarUsuario(context: Context, id: Int) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = id
        }
    }

    fun lerUsuario(context: Context): Flow<Int?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID]
        }
    }
}
