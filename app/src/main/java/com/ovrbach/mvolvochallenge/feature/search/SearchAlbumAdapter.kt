package com.ovrbach.mvolvochallenge.feature.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumItemBinding
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem

class SearchAlbumAdapter(
    private val onItemClick: (AlbumItem) -> Unit
) : ListAdapter<AlbumItem, SearchAlbumAdapter.ViewHolder>(
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
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: SearchAlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(getItem(adapterPosition))
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

    }
}