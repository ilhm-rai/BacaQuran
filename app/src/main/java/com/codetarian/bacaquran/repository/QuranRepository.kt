package com.codetarian.bacaquran.repository

import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.dao.QuranDao
import com.codetarian.bacaquran.domain.SurahDomain

class QuranRepository(private val quranDao: QuranDao) {
    val surahItems: MutableList<SurahDomain> = mutableListOf(
        SurahDomain(1, "1","1", "Al-Fātiḥah", "Al-Fatihah", "Pembuka", 7, 1, "Makkiyah", null),
        SurahDomain(2,  "1", "2", "Al-Fātiḥah", "Al-Baqarah", "Sapi Betina", 7, 1, "Makkiyah", null),
        SurahDomain(3, "1","3", "Al-Fātiḥah", "Ali ‘Imran", "Keluarga Imran", 7, 1, "Makkiyah", null),
        SurahDomain(4, "1", "4", "Al-Fātiḥah", "An-Nisa'", "Wanita", 7, 1, "Makkiyah", null),
    )

    val allSurah: LiveData<List<SurahDomain>> = quranDao.getAllSurah()
}