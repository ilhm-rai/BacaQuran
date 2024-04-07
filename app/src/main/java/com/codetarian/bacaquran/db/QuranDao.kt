package com.codetarian.bacaquran.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.codetarian.bacaquran.db.entity.Juz
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.db.entity.Verse
import com.codetarian.bacaquran.db.entity.VerseAndSurah

@Dao
interface QuranDao {

    @Query("Select * from quran_surah order by id ASC")
    fun getAllSurah(): LiveData<List<Surah>>

    @Query("Select * from quran_verse where surah_id = :surahId order by ayah ASC")
    fun getVersesBySurahId(surahId: Int): LiveData<List<Verse>>

    @Query(
        "SELECT \n" +
                "    q1.juz AS juz,\n" +
                "    q1.surah_id AS first_surah_id,\n" +
                "    q1.ayah AS first_ayah,\n" +
                "    quran_surah.transliteration AS first_surah_name,\n" +
                "    q2.surah_id AS last_surah_id,\n" +
                "    q2.ayah AS last_ayah,\n" +
                "    quran_surah2.transliteration AS last_surah_name\n" +
                "FROM \n" +
                "    (\n" +
                "        SELECT \n" +
                "            juz,\n" +
                "            MIN(rowid) AS first_ayah_rowid\n" +
                "        FROM \n" +
                "            quran_verse\n" +
                "        GROUP BY \n" +
                "            juz\n" +
                "    ) AS temp1\n" +
                "JOIN \n" +
                "    quran_verse AS q1 ON temp1.first_ayah_rowid = q1.rowid\n" +
                "JOIN \n" +
                "    quran_surah ON q1.surah_id = quran_surah.id\n" +
                "JOIN \n" +
                "    (\n" +
                "        SELECT \n" +
                "            juz,\n" +
                "            MAX(rowid) AS last_ayah_rowid\n" +
                "        FROM \n" +
                "            quran_verse\n" +
                "        GROUP BY \n" +
                "            juz\n" +
                "    ) AS temp2 ON q1.juz = temp2.juz\n" +
                "JOIN \n" +
                "    quran_verse AS q2 ON temp2.last_ayah_rowid = q2.rowid\n" +
                "JOIN \n" +
                "    quran_surah AS quran_surah2 ON q2.surah_id = quran_surah2.id\n" +
                "ORDER BY \n" +
                "    q1.juz;\n"
    )
    fun getAllJuz(): LiveData<List<Juz>>


    @Query(
        "SELECT qv.*, qs.transliteration FROM quran_verse AS qv " +
                "INNER JOIN quran_surah AS qs " +
                "ON qv.surah_id = qs.id " +
                "WHERE qv.is_bookmarked = 1 " +
                "ORDER BY ayah ASC"
    )
    fun getBookmarkedVerses(): LiveData<List<VerseAndSurah>>

    @Query(
        "UPDATE quran_verse " +
                "SET is_bookmarked = NOT is_bookmarked, " +
                "bookmarked_at = DATETIME('now', 'localtime') " +
                "WHERE id = :verseId"
    )
    fun updateVerseBookmark(verseId: Int)
}