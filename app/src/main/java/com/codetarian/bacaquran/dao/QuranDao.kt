package com.codetarian.bacaquran.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.codetarian.bacaquran.domain.SurahDomain

@Dao
interface QuranDao {

    @Query("Select * from quran_surah order by id ASC")
    fun getAllSurah(): LiveData<List<SurahDomain>>
}