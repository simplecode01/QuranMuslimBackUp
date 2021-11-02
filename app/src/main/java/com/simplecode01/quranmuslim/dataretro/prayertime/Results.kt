package com.simplecode01.quranmuslim.dataretro.prayertime


import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("datetime")
    val datetime: List<Datetime>,
    @SerializedName("location")
    val location: Location,
    @SerializedName("settings")
    val settings: Settings
)