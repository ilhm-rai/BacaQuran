package com.codetarian.bacaquran.domain

import androidx.room.ColumnInfo

data class Juz(
    @ColumnInfo(name = "juz") val juz: Int,
    @ColumnInfo(name = "first_surah_id") val firstSurahId: Int?,
    @ColumnInfo(name = "first_surah_name") val firstSurahName: String?,
    @ColumnInfo(name = "first_ayah") val firstAyah: Int?,
    @ColumnInfo(name = "last_surah_id") val lastSurahId: Int?,
    @ColumnInfo(name = "last_surah_name") val lastSurahName: String?,
    @ColumnInfo(name = "last_ayah") val lastAyah: Int?
)
