package com.simplecode01.quranmuslim.data

import com.simplecode01.quranmuslim.model.Page
import com.simplecode01.quranmuslim.model.Quran
import kotlinx.coroutines.flow.Flow

class QuranRepository(val quranDatabase: QuranDatabase) {

    fun getQuran(): Flow<List<Quran>> {
        return quranDatabase.quranDao().getQuran()
    }

    // getSurah
    // getAyah

}