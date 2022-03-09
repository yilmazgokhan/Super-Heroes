package com.yilmazgokhan.superhero.di

import com.yilmazgokhan.superhero.di.qualifier.DefaultDispatcher
import com.yilmazgokhan.superhero.di.qualifier.IoDispatcher
import com.yilmazgokhan.superhero.di.qualifier.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * The Main [Module] that holds all app core classes and provides these instances
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //region Dispatchers
    @DefaultDispatcher
    @Singleton
    @Provides
    internal fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Singleton
    @Provides
    internal fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Singleton
    @Provides
    internal fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
    //endregion
}