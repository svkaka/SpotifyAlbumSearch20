package com.ovrbach.mvolvochallenge.app.di

import com.ovrbach.mvolvochallenge.model.entity.Session
import com.ovrbach.mvolvochallenge.model.entity.SessionImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class UtilModule {

    @Provides
    fun provideSession(): Session = SessionImpl()
}