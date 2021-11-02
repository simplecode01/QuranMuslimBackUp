package com.simplecode01.quranmuslim.dataretro.date


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("gregorian")
    val gregorian: Gregorian,
    @SerializedName("hijri")
    val hijri: Hijri
)