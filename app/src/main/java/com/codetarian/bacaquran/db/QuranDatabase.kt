package com.codetarian.bacaquran.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codetarian.bacaquran.db.entity.Surah
import com.codetarian.bacaquran.db.entity.Verse

@Database(entities = [Surah::class, Verse::class], version = 3, exportSchema = false)
abstract class QuranDatabase : RoomDatabase() {

    abstract fun getQuranDao(): QuranDao

    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: QuranDatabase? = null

        fun getDatabase(context: Context): QuranDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuranDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        const val DB_NAME = "quran_database.db"
    }
}