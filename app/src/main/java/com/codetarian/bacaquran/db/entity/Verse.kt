package com.codetarian.bacaquran.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "quran_verse")
data class Verse(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "surah_id") var surahId: Int,
    @ColumnInfo(name = "ayah") var ayah: Int,
    @ColumnInfo(name = "avg_timing") var avgTiming: Float,
    @ColumnInfo(name = "juz") var juz: Int,
    @ColumnInfo(name = "arabic") var arabic: String,
    @ColumnInfo(name = "arabic_indopak") var arabicIndopak: String,
    @ColumnInfo(name = "translation") var translation: String,
    @ColumnInfo(name = "latin") var latin: String,
    @ColumnInfo(name = "footnotes") var footnotes: String?,
    @ColumnInfo(name = "is_bookmarked") var isBookmarked: Boolean = false,
    @ColumnInfo(name = "bookmarked_at") var bookmarkedAt: String?,
) : Parcelable
