package com.codetarian.bacaquran.db.repository

import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.db.QuranDao
import com.codetarian.bacaquran.db.entity.Juz
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.db.entity.Verse
import com.codetarian.bacaquran.db.entity.VerseAndSurah
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(private val quranDao: QuranDao) : QuranRepository {

    override fun observeAllSurah(): LiveData<List<Surah>> = quranDao.getAllSurah()
    override fun observeVersesBySurahId(surahId: Int): LiveData<List<Verse>> = quranDao.getVersesBySurahId(surahId)
    override fun observeAllJuz(): LiveData<List<Juz>> = quranDao.getAllJuz()
    override fun observeBookmarkedVerses(): LiveData<List<VerseAndSurah>> = quranDao.getBookmarkedVerses()
    override suspend fun updateVerseBookmark(verseId: Int) = quranDao.updateVerseBookmark(verseId)
}