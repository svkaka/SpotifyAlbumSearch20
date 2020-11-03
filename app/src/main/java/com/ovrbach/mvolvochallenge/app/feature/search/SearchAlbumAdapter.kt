package com.ovrbach.mvolvochallenge.app.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumItemBinding
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem

class SearchAlbumAdapter(
    private val onItemClick: (AlbumItem) -> Unit
) : PagingDataAdapter<AlbumItem, SearchAlbumAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean =
            oldItem == newItem

    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            SearchAlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item -> holder.bind(item) } ?: holder.placeholder()
    }

    inner class ViewHolder(
        private val binding: SearchAlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { item -> onItemClick(item) }
            }
        }

        fun bind(item: AlbumItem) {
            with(binding) {
                artists.text = item.artists.joinToString { it.name }
                name.text = item.name
                releaseDate.text = item.releaseDate
                image.load(item.images.firstOrNull()?.url)
            }
        }

        fun placeholder() {
            with(binding) {
                artists.text = "Loading"
            }
        }

    }
}