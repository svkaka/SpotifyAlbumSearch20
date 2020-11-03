package com.ovrbach.mvolvochallenge.app.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.ovrbach.mvolvochallenge.model.entity.Session
import com.ovrbach.mvolvochallenge.model.entity.SessionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UtilModule {

    @Provides
    @Singleton
    fun provideSession(): Session = SessionImpl()

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources = context.resources
}