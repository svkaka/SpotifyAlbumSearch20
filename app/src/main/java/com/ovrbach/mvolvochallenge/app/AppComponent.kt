package com.ovrbach.mvolvochallenge.app

import com.ovrbach.mvolvochallenge.data.AlbumRepository
import com.ovrbach.mvolvochallenge.model.mapper.AlbumResponseMapper
import com.ovrbach.mvolvochallenge.model.mapper.ArtistMapper
import com.ovrbach.mvolvochallenge.model.mapper.ImageMapper
import com.ovrbach.mvolvochallenge.remote.AlbumService
import com.ovrbach.mvolvochallenge.remote.apiRetrofit
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RemoteModule {

    @Provides
    fun provideAlbumService(): AlbumService = apiRetrofit.create(AlbumService::class.java)


}

@Module
@InstallIn(ApplicationComponent::class)
class MapperModule {

//    @Provides
//    fun provideAlbumMapper(
//        artistMapper: ArtistMapper,
//        imageMapper: ImageMapper
//    ): AlbumResponseMapper =
//        AlbumResponseMapper(artistMapper = artistMapper, imageMapper = imageMapper)

    @Provides
    fun provideArtistMapper(): ArtistMapper = ArtistMapper()

    @Provides
    fun provideImageMapper(): ImageMapper = ImageMapper()
}

class RepositoryModule {
}