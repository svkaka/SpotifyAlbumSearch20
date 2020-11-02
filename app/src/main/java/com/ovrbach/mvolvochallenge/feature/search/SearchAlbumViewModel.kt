package com.ovrbach.mvolvochallenge.feature.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ovrbach.mvolvochallenge.data.AlbumRepository
import com.ovrbach.mvolvochallenge.data.Outcome
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.util.LiveEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchAlbumViewModel @ViewModelInject constructor(
    private val albumService: AlbumRepository
) : ViewModel() {

    val state: MutableLiveData<State> = MutableLiveData()
    val sideEffects: MutableLiveData<SideEffect?> = MutableLiveData()
    val signal: LiveEvent<Signal?> = LiveEvent()

    private val inputFlow = MutableStateFlow<String?>(null)
    private val sideEffectFlow = MutableStateFlow<SideEffect?>(null)
    private val searchFlow = inputFlow
        .debounce(500)
        .mapLatest { input ->
            sideEffectFlow.value =
                SideEffect.Loading
            if (input.isNullOrEmpty()) {
                State.Empty
            } else {
                when (val result = albumService.searchAlbums(input)) {
                    is Outcome.Success -> if (result.data.isEmpty()) {
                        State.Empty
                    } else {
                        State.Success(
                            items = result.data
                        )
                    }
                    is Outcome.Failed -> State.Failed(
                        throwable = result.throwable
                    )
                }
            }
        }.onEach {
            sideEffectFlow.value = null
        }

    init {
        viewModelScope.launch {
            searchFlow.collect { result ->
                state.postValue(result)
            }
        }

        viewModelScope.launch {
            sideEffectFlow.collect { effects ->
                sideEffects.postValue(effects)
            }
        }
    }

    fun search(input: String?) {
        viewModelScope.launch {
            inputFlow.value = input
        }
    }

    fun onItemClick(item: AlbumItem) {
        signal.postValue(Signal.OpenDetails(item))
    }

    sealed class State {
        object Empty : State()
        data class Failed(val throwable: Throwable) : State()
        data class Success(val items: List<AlbumItem>) : State()
    }

    sealed class SideEffect {
        object Loading : SideEffect()
    }

    sealed class Signal {
        data class OpenDetails(val albumItem: AlbumItem) : Signal()
    }
}