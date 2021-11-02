package com.simplecode01.quranmuslim.dataapiretro.apiDate

import com.google.gson.Gson
import com.simplecode01.quranmuslim.dataretro.date.hijrihDate
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HijrihDateAPI {

    @GET("gToH")
    suspend fun getHijrihDate(
        @Query("date")date : String,
        @Query("adjustment")adjustment: Int
    ): hijrihDate

    companion object{
        const val BASE_URL = "https://api.aladhan.com/v1/"

        fun create() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(HijrihDateAPI:: class.java)
    }

}