package com.codetarian.bacaquran.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codetarian.bacaquran.config.QuranDatabase
import com.codetarian.bacaquran.domain.SurahDomain
import com.codetarian.bacaquran.repository.QuranRepository

class SurahViewModel(application: Application) : AndroidViewModel(application) {

    val allSurah : LiveData<List<SurahDomain>>
    private val repository : QuranRepository

    init {
        val dao = QuranDatabase.getDatabase(application).getQuranDao()
        repository = QuranRepository(dao)
        allSurah = repository.allSurah
    }

    fun loadSurah() = repository.surahItems
}