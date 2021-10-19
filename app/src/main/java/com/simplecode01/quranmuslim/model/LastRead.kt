package com.simplecode01.quranmuslim.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_read")
data class LastRead(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "posisition") val posisitionScroll: Int,
    @ColumnInfo(name = "sora_name_en") val nameSurah: String,
    @ColumnInfo(name = "sora_no") val surahNumber: Int,
    @ColumnInfo(name = "aya_no") val ayahNumber: Int,

)
