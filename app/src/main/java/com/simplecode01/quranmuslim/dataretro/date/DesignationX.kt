package com.simplecode01.quranmuslim.dataretro.date


import com.google.gson.annotations.SerializedName

data class DesignationX(
    @SerializedName("abbreviated")
    val abbreviated: String,
    @SerializedName("expanded")
    val expanded: String
)