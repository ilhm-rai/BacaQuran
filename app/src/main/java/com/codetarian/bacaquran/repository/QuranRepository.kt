package com.codetarian.bacaquran.repository

import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.dao.QuranDao
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.domain.Verse

class QuranRepository(private val quranDao: QuranDao) {

    val allSurah: LiveData<List<Surah>> = quranDao.getAllSurah()
    fun getSurahVerses(surahId: Int): LiveData<List<Verse>> = quranDao.getSurahVerses(surahId)
}