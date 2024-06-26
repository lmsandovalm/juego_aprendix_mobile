package com.laurasando.juego_aprendix_mobile.data.local

import android.content.Context
import android.content.SharedPreferences
import java.lang.IllegalArgumentException


class SharePreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("DB_APRENDIX_LOCAL", Context.MODE_PRIVATE)
    }

    fun savePref(key: String, value: Any) {
        val editor = sharedPreferences.edit()
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Long -> editor.putLong(key, value)
            is Float -> editor.putFloat(key, value)
            is Boolean -> editor.putBoolean(key, value)
            else -> throw IllegalArgumentException("Este tipo de dato no es compatible")
        }
        editor.apply()
    }

    fun getPref(key: String, defaultValue: Any): Any {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue)
            is Int -> sharedPreferences.getInt(key, defaultValue)
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue)
            is Float -> sharedPreferences.getFloat(key, defaultValue)
            is Long -> sharedPreferences.getLong(key, defaultValue)
            else -> throw IllegalArgumentException("No se puede obtener este tipo de dato")
        }!!
    }

    fun remove(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}