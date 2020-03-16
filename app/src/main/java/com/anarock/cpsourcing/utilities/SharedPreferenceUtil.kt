package com.anarock.cpsourcing.utilities

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) {
    private val CPSOUCING_APP_SHARED_PREFERENCE = "cpSourcingAppSharedPreference"
    private var sharedpreferences: SharedPreferences =
        context.getSharedPreferences(CPSOUCING_APP_SHARED_PREFERENCE, Context.MODE_PRIVATE)

    companion object {
        private lateinit var INSTANCE: SharedPreferenceUtil

       /* val AUTHORIZATION_KEY = "authorizationKey"
        val USER_ID = "UserId"*/

        fun getInstance(context: Context): SharedPreferenceUtil {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = SharedPreferenceUtil(context)
            }
            return INSTANCE
        }
    }

    fun putString(key: String, value: String?) {
        val editor = sharedpreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun putInt(key: String, value: Int) {
        val editor = sharedpreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun putFloat(key: String, value: Float) {
        val editor = sharedpreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun putLong(key: String, value: Long) {
        val editor = sharedpreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val editor = sharedpreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedpreferences.getString(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedpreferences.getInt(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedpreferences.getFloat(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedpreferences.getLong(key, defaultValue)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedpreferences.getBoolean(key, defaultValue)
    }
}