package com.seunggyu.stitch.Util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.seunggyu.stitch.data.model.User

class MySharedPreference(context: Context) {
    private val PREFS_FILE_NAME = "prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, 0)

    private val DEFAULT_STRING_VALUE = ""
    private val DEFAULT_INT_VALUE = 0
    private val DEFAULT_BOOLEAN_VALUE = false
    private val DEFAULT_FLOAT_VALUE = 0.0F
    private val DEFAULT_LONG_VALUE = 0L

    fun getString(key: String): String? {
        return prefs.getString(key, DEFAULT_STRING_VALUE)
    }

    fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getStringList(key: String): List<String> {
        val savedString = prefs.getString(key, DEFAULT_STRING_VALUE)
        return savedString?.split(",") ?: emptyList()
    }

    fun setStringList(key: String, value: List<String>) {
        prefs.edit().putString(key, value.joinToString(separator = ",")).apply()
    }

    fun getInt(key: String): Int {
        return prefs.getInt(key, DEFAULT_INT_VALUE)
    }

    fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return prefs.getBoolean(key, DEFAULT_BOOLEAN_VALUE)
    }

    fun setBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getFloat(key: String): Float {
        return prefs.getFloat(key, DEFAULT_FLOAT_VALUE)
    }

    fun setFloat(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }

    fun getLong(key: String): Long {
        return prefs.getLong(key, DEFAULT_LONG_VALUE)
    }

    fun setLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun getCurrentUser(): User {
        val sportsList = prefs.getString("sports", "")?.split(",") ?: listOf()
        return User(
            prefs.getString("userId", DEFAULT_STRING_VALUE)!!,
            prefs.getString("type", DEFAULT_STRING_VALUE)!!,
            prefs.getString("name", DEFAULT_STRING_VALUE)!!,
            prefs.getString("imageUrl", DEFAULT_STRING_VALUE)!!,
            prefs.getString("location", DEFAULT_STRING_VALUE)!!,
            sportsList,
            prefs.getString("token", DEFAULT_STRING_VALUE)!!,
            prefs.getString("introduce", DEFAULT_STRING_VALUE)!!,
            )
    }
}