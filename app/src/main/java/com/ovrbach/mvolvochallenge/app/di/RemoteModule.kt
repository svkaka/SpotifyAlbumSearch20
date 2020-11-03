package com.ovrbach.mvolvochallenge.app.di

import com.ovrbach.mvolvochallenge.app.di.qualifier.ClientId
import com.ovrbach.mvolvochallenge.app.di.qualifier.ClientSecret
import com.ovrbach.mvolvochallenge.remote.AuthInterceptor
import com.ovrbach.mvolvochallenge.remote.AuthService
import com.ovrbach.mvolvochallenge.remote.RemoteServiceConstants
import com.ovrbach.mvolvochallenge.remote.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class RemoteModule {

    @Provides
    fun provideAuthService(): AuthService = Retrofit.Builder()
        .baseUrl(RemoteServiceConstants.BASE_AUTH_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(OkHttpClient()).build().create(AuthService::class.java)

    @Provides
    fun provideApiClient(authInterceptor: AuthInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(RemoteServiceConstants.BASE_API_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideAlbumService(apiRetrofit: Retrofit): SearchService =
        apiRetrofit.create(SearchService::class.java)

    @Provides
    @ClientSecret
    fun provideSecret() = RemoteServiceConstants.SECRET

    @Provides
    @ClientId
    fun provideClientId() = RemoteServiceConstants.CLIENT_CODE

}
