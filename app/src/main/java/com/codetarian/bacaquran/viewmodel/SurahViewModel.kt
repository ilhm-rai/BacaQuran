package com.codetarian.bacaquran.viewmodel

import androidx.lifecycle.ViewModel
import com.codetarian.bacaquran.repository.QuranRepository

class SurahViewModel(val repository: QuranRepository) : ViewModel() {

    fun loadSurah() = repository.surahItems
}