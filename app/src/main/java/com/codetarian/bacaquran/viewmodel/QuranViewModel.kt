package com.codetarian.bacaquran.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.config.QuranDatabase
import com.codetarian.bacaquran.domain.Juz
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.repository.QuranRepository

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : QuranRepository

    init {
        val dao = QuranDatabase.getDatabase(application).getQuranDao()
        repository = QuranRepository(dao)
    }

    fun loadSurahData(): LiveData<List<Surah>> = repository.observeAllSurah()

    fun loadJuzData(): LiveData<List<Juz>> = repository.observeAllJuz()

    fun loadVersesBySurah(surahId: Int) = repository.observeVersesBySurahId(surahId)
}