package com.crxapplications.cardform.di

import com.crxapplications.cardform.ui.flows.cardform.validators.CardValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideCardValidator(): CardValidator {
        return CardValidator()
    }
}