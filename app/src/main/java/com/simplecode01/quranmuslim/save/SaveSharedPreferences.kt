package com.simplecode01.quranmuslim.save

import android.content.Context
import android.preference.PreferenceManager

class SaveSharedPreferences(context: Context) {

    companion object{
        const val KEY_DARK_STATUS = "darkMode"
        const val KEY_CHANGE_QORI = "0"
        const val KEY_TEXT_SIZE = "10pt"
    }
    private val penampung_size = PreferenceManager.getDefaultSharedPreferences(context)

    var darkMode = penampung_size.getInt(KEY_DARK_STATUS, 0)
        set(value) = penampung_size.edit().putInt(KEY_DARK_STATUS, value).apply()

    var ganti_font = penampung_size.getInt(KEY_TEXT_SIZE, 10)
        set(value) = penampung_size.edit().putInt(KEY_TEXT_SIZE, value).apply()

    var ganti_qori = penampung_size.getInt(KEY_CHANGE_QORI, 0)
        set(value) = penampung_size.edit().putInt(KEY_CHANGE_QORI, value).apply()
}