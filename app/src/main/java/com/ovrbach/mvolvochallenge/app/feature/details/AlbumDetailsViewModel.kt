package com.ovrbach.mvolvochallenge.app.feature.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovrbach.mvolvochallenge.data.AlbumRepository
import com.ovrbach.mvolvochallenge.data.Outcome
import com.ovrbach.mvolvochallenge.model.entity.AlbumDetails
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import kotlinx.coroutines.launch

class AlbumDetailsViewModel @ViewModelInject constructor(
        private val albumService: AlbumRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData()

    init {
        val albumItem: AlbumItem = savedStateHandle.get(AlbumDetailsFragment.ALBUM_ITEM)!!
        state.postValue(State.PartiallyLoaded(albumItem))

        viewModelScope.launch {
            state.postValue(
                    when (val outcome = albumService.albumDetails(albumItem.id)) {
                        is Outcome.Success -> State.Loaded(outcome.data)
                        is Outcome.Failed -> State.Failed(outcome.throwable)
                    })
        }
    }

    sealed class State {
        data class PartiallyLoaded(val albumItem: AlbumItem) : State()
        data class Loaded(val albumDetails: AlbumDetails) : State()
        data class Failed(val throwable: Throwable) : State()
    }
}