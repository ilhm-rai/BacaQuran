package com.codetarian.bacaquran.di

import android.content.Context
import androidx.room.Room
import com.codetarian.bacaquran.db.QuranDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesQuranDatabase(@ApplicationContext context: Context): QuranDatabase {
        return Room.databaseBuilder(context, QuranDatabase::class.java, QuranDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providesQuranDao(quranDatabase: QuranDatabase) = quranDatabase.getQuranDao()
}