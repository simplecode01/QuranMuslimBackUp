package com.simplecode01.quranmuslim.dataapiretro.apiPrayer

import com.google.gson.Gson
import com.simplecode01.quranmuslim.dataretro.prayertime.PrayerTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerAPI {
    @GET("today.json")
    suspend fun getPrayerTime(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): PrayerTime
    companion object{
        const val BASE_URL = "https://api.pray.zone/v2/times/"

        fun create() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(PrayerAPI:: class.java)
    }
}