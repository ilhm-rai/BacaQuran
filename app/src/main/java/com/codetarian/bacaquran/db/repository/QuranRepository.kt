package com.codetarian.bacaquran.db.repository

import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.db.entity.Juz
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.db.entity.Verse

interface QuranRepository {
    fun observeAllSurah(): LiveData<List<Surah>>
    fun observeVersesBySurahId(surahId: Int): LiveData<List<Verse>>
    fun observeAllJuz(): LiveData<List<Juz>>
}