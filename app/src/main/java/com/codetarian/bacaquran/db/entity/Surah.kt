package com.codetarian.bacaquran.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "quran_surah")
data class Surah(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "quran_surah_font") var quranSurahFont: String,
    @ColumnInfo(name = "latin") var latin: String,
    @ColumnInfo(name = "transliteration") var transliteration: String,
    @ColumnInfo(name = "translation") var translation: String,
    @ColumnInfo(name = "num_ayah") var numAyah: Int,
    @ColumnInfo(name = "page") var page: Int,
    @ColumnInfo(name = "location") var location: String,
    @ColumnInfo(name = "first_juz") var firstJuz: Int,
    @ColumnInfo(name = "updated_at") var updatedAt: String?
) : Parcelable
