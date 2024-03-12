package com.codetarian.bacaquran.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran_surah")
data class SurahDomain(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "font_type") var fontType: String,
    @ColumnInfo(name = "arabic_font_code") var arabicFontCode: String,
    @ColumnInfo(name = "latin") var latin: String,
    @ColumnInfo(name = "transliteration") var transliteration: String,
    @ColumnInfo(name = "translation") var translation: String,
    @ColumnInfo(name = "num_ayah") var numAyah: Int,
    @ColumnInfo(name = "page") var page: Int,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "updated_at") var updatedAt: String?
)
