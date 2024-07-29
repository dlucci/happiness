package com.dlucci.aaviainterview

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AaviaModule {

    @Provides
    @Singleton
    fun providesSharedPrefs(@ApplicationContext context : Context) = context.getSharedPreferences("aavia", Context.MODE_PRIVATE)

}