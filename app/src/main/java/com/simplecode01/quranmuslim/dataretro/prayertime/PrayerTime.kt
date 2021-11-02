package com.simplecode01.quranmuslim.dataretro.prayertime


import com.google.gson.annotations.SerializedName

data class PrayerTime(
    @SerializedName("code")
    val code: Int,
    @SerializedName("results")
    val results: Results,
    @SerializedName("status")
    val status: String
)