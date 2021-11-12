package com.simplecode01.quranmuslim.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    @ColumnInfo(name = "posisition") val posisitionScrollBookmark: Int,
    @ColumnInfo(name = "sora_name_en") val nameSurah: String,
    @ColumnInfo(name = "sora_no") val surahNumber: Int,
    @ColumnInfo(name = "aya_no") val ayahNumber: Int,
    val timestamp: Long,
)
