package com.codetarian.bacaquran.repository

import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.dao.QuranDao
import com.codetarian.bacaquran.domain.Juz
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.domain.Verse

class QuranRepository(private val quranDao: QuranDao) {

    fun observeAllSurah(): LiveData<List<Surah>> = quranDao.getAllSurah()
    fun observeVersesBySurahId(surahId: Int): LiveData<List<Verse>> = quranDao.getVersesBySurahId(surahId)
    fun observeAllJuz(): LiveData<List<Juz>> = quranDao.getAllJuz()
}