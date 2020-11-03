package com.ovrbach.mvolvochallenge.feature.search

import androidx.paging.PagingSource
import com.ovrbach.mvolvochallenge.data.AlbumRepository
import com.ovrbach.mvolvochallenge.data.Outcome
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem

class SearchPagingSource(
        private val repository: AlbumRepository,
        private val input: String?
) : PagingSource<Int, AlbumItem>() {
    override suspend fun load(
            params: LoadParams<Int>
    ): LoadResult<Int, AlbumItem> {
        val nextPageNumber = params.key ?: 0
        return when (val outcome = repository.searchAlbums(
                query = input,
                offset = nextPageNumber,
                limit = params.loadSize
        )) {
            is Outcome.Success -> return LoadResult.Page(
                    data = outcome.data.albums,
                    prevKey = null, // Only paging forward.
                    nextKey = outcome.data.next
            )
            is Outcome.Failed -> LoadResult.Error(outcome.throwable)
        }

    }

}