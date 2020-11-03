package com.ovrbach.mvolvochallenge.feature.search

import android.net.Uri
import androidx.paging.PagingSource
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.mapper.AlbumResponseMapper
import com.ovrbach.mvolvochallenge.remote.SearchService

class SearchPagingSource(
        private val backend: SearchService,
        private val input: String?,
        private val responseMapper: AlbumResponseMapper
) : PagingSource<Int, AlbumItem>() {
    override suspend fun load(
            params: LoadParams<Int>
    ): LoadResult<Int, AlbumItem> {
        try {
            val nextPageNumber = params.key ?: 0
            val response = backend.getAlbums(
                    query = input,
                    offset = nextPageNumber,
                    limit = params.loadSize
            )
            val data = responseMapper.toDomain(response)
            val nextPage = parseNextOffset(response.albums.next)
            return LoadResult.Page(
                    data = data,
                    prevKey = null, // Only paging forward.
                    nextKey = nextPage
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun parseNextOffset(next: String) = try {
        Uri.parse(next).let { uri -> uri.getQueryParameter("offset")?.toInt() }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        null
    }

}