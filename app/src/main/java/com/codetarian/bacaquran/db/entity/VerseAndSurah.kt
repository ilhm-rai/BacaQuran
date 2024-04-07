package com.codetarian.bacaquran.db.entity

import androidx.room.Embedded

data class VerseAndSurah(
    val transliteration: String,
    @Embedded val verse: Verse
)
