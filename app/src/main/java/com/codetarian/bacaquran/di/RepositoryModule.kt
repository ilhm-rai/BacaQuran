package com.codetarian.bacaquran.di

import com.codetarian.bacaquran.db.QuranDao
import com.codetarian.bacaquran.db.repository.QuranRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun providesQuranRepository(quranDao: QuranDao) = QuranRepositoryImpl(quranDao)
}