package com.example.diplomaproject.core.repository

import android.content.Context
import javax.inject.Inject

class SharedPreferencesRepo @Inject constructor(
    context: Context
) {

    companion object {
        const val MY_PREFS_NAME: String = "MyPrefsFile"
        const val KEY_USER_NAME: String = "user_name"
        const val KEY_USER_EMAIL: String = "user_email"
        const val KEY_USER_IMAGE: String = "user_image"
        const val KEY_USER_TOKEN: String = "user_token"
        const val FIRST_LAUNCH_INSTRUCTION: String = "first_launch_instruction"
        const val NO_VALUE = ""
    }

    private val prefs = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)

    fun getUsername(): String = prefs.getString(KEY_USER_NAME, NO_VALUE) ?: NO_VALUE

    fun setUsername(username: String) { prefs.edit().putString(KEY_USER_NAME, username).apply() }

    fun getUserEmail(): String = prefs.getString(KEY_USER_EMAIL, NO_VALUE) ?: NO_VALUE

    fun setUserEmail(email: String) { prefs.edit().putString(KEY_USER_EMAIL, email).apply() }

    fun getUserImage(): String = prefs.getString(KEY_USER_IMAGE, NO_VALUE) ?: NO_VALUE

    fun setUserImage(image: String) { prefs.edit().putString(KEY_USER_IMAGE, image).apply() }

    fun getUserToken(): String = prefs.getString(KEY_USER_TOKEN, NO_VALUE) ?: NO_VALUE

    fun setUserToken(token: String) { prefs.edit().putString(KEY_USER_TOKEN, token).apply() }

    fun getFirstLaunchInstruction(): Boolean = prefs.getBoolean(FIRST_LAUNCH_INSTRUCTION, true)

    fun setFirstLaunchInstruction(value: Boolean) { prefs.edit().putBoolean(FIRST_LAUNCH_INSTRUCTION, value).apply() }

    fun clearAll() { prefs.edit().clear().apply() }
}