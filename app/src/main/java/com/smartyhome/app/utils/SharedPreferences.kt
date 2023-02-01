package com.smartyhome.app.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.smartyhome.app.main.login.LoginApiResponse

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "kotlincodes"
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    var gson = Gson()

    fun setString(KEY_NAME: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }


    fun setLong(KEY_NAME: String, _time: Long) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putLong(KEY_NAME, _time)
        editor!!.commit()
    }

    fun setBoolean(KEY_NAME: String, _booleanValue: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(KEY_NAME, _booleanValue)
        editor!!.commit()
    }


    fun getLong(KEY_NAME: String): Long? {
        return sharedPref.getLong(KEY_NAME, 0)
    }

    fun getBoolean(KEY_NAME: String): Boolean? {
        return sharedPref.getBoolean(KEY_NAME, false)
    }


    fun getString(KEY_NAME: String): String? {
        return sharedPref.getString(KEY_NAME, null)
    }

    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_NAME: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(KEY_NAME)
        editor.commit()
    }

    fun saveProfileinfo(loginInfo:LoginApiResponse){
        val _str_info = gson.toJson(loginInfo)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(Constant.LOGIN_INFO, _str_info)
        editor!!.commit()
    }


    fun getLoginInfo(): LoginApiResponse? {
        val str = sharedPref.getString(Constant.LOGIN_INFO, "")
        if (str.isNullOrEmpty())
            return null
        val subscriptionInfo = gson.fromJson<LoginApiResponse>(str, LoginApiResponse::class.java)
        return subscriptionInfo
    }

    fun getUDID(): String {
        return getLoginInfo()?.UID ?: ""
    }
}