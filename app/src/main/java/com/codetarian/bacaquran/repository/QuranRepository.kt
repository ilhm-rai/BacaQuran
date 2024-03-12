package com.codetarian.bacaquran.repository

import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.dao.QuranDao
import com.codetarian.bacaquran.domain.SurahDomain

class QuranRepository(private val quranDao: QuranDao) {
    val surahItems: MutableList<SurahDomain> = mutableListOf(
        SurahDomain("الفاتحة", "Al-Fātiḥah", "Al-Fatihah", "Pembuka", 7, 1, "Makkiyah"),
        SurahDomain( "الفاتحة", "Al-Fātiḥah", "Al-Baqarah", "Sapi Betina", 7, 1, "Makkiyah"),
        SurahDomain("الفاتحة", "Al-Fātiḥah", "Ali ‘Imran", "Keluarga Imran", 7, 1, "Makkiyah"),
        SurahDomain("الفاتحة", "Al-Fātiḥah", "An-Nisa'", "Wanita", 7, 1, "Makkiyah"),
    )

    val allSurah: LiveData<List<SurahDomain>> = quranDao.getAllSurah()
}