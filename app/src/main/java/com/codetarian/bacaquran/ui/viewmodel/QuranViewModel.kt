package com.codetarian.bacaquran.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.codetarian.bacaquran.db.QuranDatabase
import com.codetarian.bacaquran.db.entity.Juz
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.db.repository.QuranRepositoryImpl

class QuranViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : QuranRepositoryImpl

    init {
        val dao = QuranDatabase.getDatabase(application).getQuranDao()
        repository = QuranRepositoryImpl(dao)
    }

    fun loadSurahData(): LiveData<List<Surah>> = repository.observeAllSurah()

    fun loadJuzData(): LiveData<List<Juz>> = repository.observeAllJuz()

    fun loadVersesBySurah(surahId: Int) = repository.observeVersesBySurahId(surahId)
}