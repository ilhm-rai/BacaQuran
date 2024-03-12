package com.codetarian.bacaquran.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran_surah")
data class SurahDomain(
    @ColumnInfo(name = "arabic") var arabic: String,
    @ColumnInfo(name = "latin") var latin: String,
    @ColumnInfo(name = "transliteration") var transliteration: String,
    @ColumnInfo(name = "translation") var translation: String,
    @ColumnInfo(name = "num_ayah") var numAyah: Int,
    @ColumnInfo(name = "page") var page: Int,
    @ColumnInfo(name = "location") var location: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 1
}
