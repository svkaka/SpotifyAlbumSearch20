package com.ovrbach.mvolvochallenge.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumStateErrorItemBinding
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumStateLoadingItemBinding

class SearchAlbumStateAdapter(
        private val onRetryClick: () -> Unit
) : LoadStateAdapter<SearchAlbumStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder = when (loadState) {
        is LoadState.NotLoading -> LoadingViewHolder(
                SearchAlbumStateLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        LoadState.Loading -> LoadingViewHolder(
                SearchAlbumStateLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        is LoadState.Error -> ErrorViewHolder(
                SearchAlbumStateErrorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    abstract inner class ViewHolder(
            view: View
    ) : RecyclerView.ViewHolder(view) {
        abstract fun bind(state: LoadState)
    }

    inner class LoadingViewHolder(
            private val binding: SearchAlbumStateLoadingItemBinding
    ) : ViewHolder(binding.root) {

        override fun bind(state: LoadState) {
            when(loadState){
                is LoadState.NotLoading -> binding.progress.isVisible = false
                LoadState.Loading ->  binding.progress.isVisible = true
                else -> throw IllegalStateException("Only loading and not-loading supported by this viewholder")
            }
        }
    }

    inner class ErrorViewHolder(
            private val binding: SearchAlbumStateErrorItemBinding
    ) : ViewHolder(binding.root) {

        init {
            binding.retry.setOnClickListener {
                onRetryClick()
            }
        }

        override fun bind(state: LoadState) {
            binding.error.text = (state as LoadState.Error).error.message
        }

    }
}