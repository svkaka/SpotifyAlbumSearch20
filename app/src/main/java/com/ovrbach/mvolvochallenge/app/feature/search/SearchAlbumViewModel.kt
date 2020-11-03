package com.ovrbach.mvolvochallenge.app.feature.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.ovrbach.mvolvochallenge.data.AlbumRepository
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.mapper.AlbumResponseMapper
import com.ovrbach.mvolvochallenge.remote.RemoteServiceConstants
import com.ovrbach.mvolvochallenge.core.util.LiveEvent
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchAlbumViewModel @ViewModelInject constructor(
        private val albumRepository: AlbumRepository
) : ViewModel() {

    val signal: LiveEvent<Signal?> = LiveEvent()
    private val inputFlow = MutableStateFlow<String?>(null)
    val searchEnabled = inputFlow.map { value -> !value.isNullOrEmpty() }.asLiveData()
    val pages = MutableLiveData<PagingData<AlbumItem>>(PagingData.empty())

    fun submitSearch(query: String?) {
        viewModelScope.launch {
            Pager(
                PagingConfig(pageSize = RemoteServiceConstants.ALBUMS_REQUEST_LIMIT)
            ) {
                SearchPagingSource(albumRepository, query)
            }.flow.cachedIn(viewModelScope).collect {
                pages.value = it
            }
        }
    }

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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
