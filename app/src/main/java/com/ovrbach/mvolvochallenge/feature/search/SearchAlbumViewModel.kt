package com.ovrbach.mvolvochallenge.feature.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.ovrbach.mvolvochallenge.data.AlbumRepository
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.mapper.AlbumResponseMapper
import com.ovrbach.mvolvochallenge.remote.RemoteServiceConstants
import com.ovrbach.mvolvochallenge.remote.SearchService
import com.ovrbach.mvolvochallenge.util.LiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchAlbumViewModel @ViewModelInject constructor(
        private val albumService: AlbumRepository,
        private val searchService: SearchService,
        private val albumResponseMapper: AlbumResponseMapper
) : ViewModel() {

    val signal: LiveEvent<Signal?> = LiveEvent()
    private val inputFlow = MutableStateFlow<String?>(null)
    val searchEnabled = inputFlow.map { value -> !value.isNullOrEmpty() }

    fun submitSearch(query: String?) =
            Pager(
                PagingConfig(pageSize = RemoteServiceConstants.ALBUMS_REQUEST_LIMIT)
            ) {
                SearchPagingSource(searchService, query, albumResponseMapper)
            }.flow.cachedIn(viewModelScope)

    fun onInputChanged(input: String?) {
        viewModelScope.launch {
            inputFlow.value = input
        }
    }

    fun onItemClick(item: AlbumItem) {
        signal.postValue(Signal.OpenDetails(item))
    }

    sealed class Signal {
        data class OpenDetails(val albumItem: AlbumItem) : Signal()
    }
}
