package com.simplecode01.quranmuslim.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.simplecode01.quranmuslim.R
import com.simplecode01.quranmuslim.model.*

@Database(entities = arrayOf(Quran::class, LastRead::class, Bookmark::class),
    views = arrayOf(Surah::class, Juz::class, Page::class),
    version = 1)
abstract class QuranDatabase : RoomDatabase(){
    abstract fun quranDao(): QuranDao

    companion object{
        @Volatile private var INSTANCE: QuranDatabase ?= null

        fun getInstance(context: Context): QuranDatabase =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(context, QuranDatabase::class.java, "quran.db")
                    .createFromInputStream {
                        context.resources.openRawResource(R.raw.quran)
                    }
                    .build()
            }
    }
}