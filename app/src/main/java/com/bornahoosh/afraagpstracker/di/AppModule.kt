package com.bornahoosh.afraagpstracker.di

import android.content.Context
import com.bornahoosh.afraagpstracker.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        @ApplicationContext context: Context
    ): LoginRepository {
        return LoginRepository(context)
    }
}
