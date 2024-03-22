package com.codetarian.bacaquran.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.codetarian.bacaquran.domain.Surah
import com.codetarian.bacaquran.domain.Verse

@Dao
interface QuranDao {

    @Query("Select * from quran_surah order by id ASC")
    fun getAllSurah(): LiveData<List<Surah>>

    @Query("Select * from quran_verse where surah_id = :surahId order by ayah ASC")
    fun getSurahVerses(surahId: Int): LiveData<List<Verse>>
}