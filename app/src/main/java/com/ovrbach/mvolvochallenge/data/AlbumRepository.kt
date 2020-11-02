package com.ovrbach.mvolvochallenge.data

import com.ovrbach.mvolvochallenge.model.dto.details.AlbumDetailsResponse
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.mapper.AlbumResponseMapper
import com.ovrbach.mvolvochallenge.remote.AlbumService
import java.util.*
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val remoteSource: AlbumService,
    private val responseMapper: AlbumResponseMapper
) {

    suspend fun searchAlbums(query: String?): Outcome<List<AlbumItem>> =
        request { responseMapper.toDomain(remoteSource.getAlbums(query)) }

    suspend fun albumDetails(albumId: String): Outcome<AlbumDetailsResponse> = request {
        remoteSource.getAlbumDetails(albumId)
    }


    suspend fun <T> request(request: suspend () -> T): Outcome<T> = try {
        Outcome.Success(request())
    } catch (t: Throwable) {
        //todo better error message
        Outcome.Failed(t)
    }

}

sealed class Outcome<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Outcome<T>()
    data class Failed(val throwable: Throwable) : Outcome<Nothing>()
}