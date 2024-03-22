package com.codetarian.bacaquran.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.codetarian.bacaquran.config.QuranDatabase
import com.codetarian.bacaquran.repository.QuranRepository

class VerseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : QuranRepository

    init {
        val dao = QuranDatabase.getDatabase(application).getQuranDao()
        repository = QuranRepository(dao)
    }

    fun loadVersesBySurah(surahId: Int) = repository.getSurahVerses(surahId)
}