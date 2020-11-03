package com.ovrbach.mvolvochallenge.app.feature.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumStateItemBinding

class SearchAlbumStateAdapter(
        private val onRetryClick: () -> Unit
) : LoadStateAdapter<SearchAlbumStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder = ViewHolder(
            SearchAlbumStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(
            private val binding: SearchAlbumStateItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retry.setOnClickListener {
                onRetryClick()
            }
        }

        fun bind(state: LoadState) {
            when (state) {
                is LoadState.NotLoading -> binding.showNotLoading(state)
                LoadState.Loading -> binding.showLoading()
                is LoadState.Error -> binding.showError(state)
            }
        }

        fun SearchAlbumStateItemBinding.showLoading() {
            binding.progressLayout.isVisible = true
            binding.progress.isVisible = true
            errorLayout.isVisible = false
        }

        fun SearchAlbumStateItemBinding.showNotLoading(state: LoadState.NotLoading) {
            binding.progressLayout.isVisible = true
            binding.progress.isVisible = false
            errorLayout.isVisible = false
        }

        fun SearchAlbumStateItemBinding.showError(state: LoadState.Error) {
            binding.progressLayout.isVisible = false
            errorLayout.isVisible = true
            error.text = state.error.message
        }
    }

}