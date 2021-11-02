package com.simplecode01.quranmuslim.dataretro.date


import com.google.gson.annotations.SerializedName

data class hijrihDate(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)