package com.ovrbach.mvolvochallenge.data

import com.ovrbach.mvolvochallenge.model.entity.AlbumDetails
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.entity.AlbumSearchResponse
import com.ovrbach.mvolvochallenge.model.mapper.AlbumDetailsMapper
import com.ovrbach.mvolvochallenge.model.mapper.AlbumResponseMapper
import com.ovrbach.mvolvochallenge.remote.SearchService
import javax.inject.Inject

class AlbumRepository @Inject constructor(
        private val remoteSource: SearchService,
        private val albumsMapper: AlbumResponseMapper,
        private val detailsMapper: AlbumDetailsMapper,
        private val errorHandler: ErrorHandler
) {

    suspend fun searchAlbums(query: String?, offset: Int, limit: Int): Outcome<AlbumSearchResponse> =
            request { albumsMapper.toDomain(remoteSource.getAlbums(query, offset = offset, limit = limit)) }

    suspend fun albumDetails(albumId: String): Outcome<AlbumDetails> = request {
        detailsMapper.toDomain(remoteSource.getAlbumDetails(albumId))
    }


    private suspend fun <T> request(request: suspend () -> T): Outcome<T> = try {
        Outcome.Success(request())
    } catch (t: Throwable) {
        t.printStackTrace()
        Outcome.Failed(errorHandler.mapError(t))
    }

}

sealed class Outcome<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : Outcome<T>()
    data class Failed(val throwable: Throwable) : Outcome<Nothing>()
}