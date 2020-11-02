package com.ovrbach.mvolvochallenge.remote

import com.ovrbach.mvolvochallenge.model.dto.details.AlbumDetailsResponse
import com.ovrbach.mvolvochallenge.model.dto.search.AlbumsSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumService {

    @GET("search/")
    suspend fun getAlbums(
        @Query("q") query: String?,
        @Query("limit") limit: Int = RemoteServiceConstants.ALBUMS_REQUEST_LIMIT,
        @Query("type") type: String = RemoteServiceConstants.ALBUMS_REQUEST_TYPE
    ): AlbumsSearchResponse

    @GET("albums/{id}")
   suspend fun getAlbumDetails(
        @Path("id") id: String
    ): AlbumDetailsResponse
}