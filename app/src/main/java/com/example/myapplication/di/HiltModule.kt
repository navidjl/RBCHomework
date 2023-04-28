package com.example.myapplication.di

import com.rbc.rbcaccountlibrary.AccountProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object MyModule {

    @Provides
    fun provideAccountProvider(): AccountProvider {
        return AccountProvider
    }

    @IoDispatcher
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher