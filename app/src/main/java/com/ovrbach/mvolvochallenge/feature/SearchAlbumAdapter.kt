package com.ovrbach.mvolvochallenge.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumFragmentBinding
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumItemBinding
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem

class SearchAlbumAdapter() : ListAdapter<AlbumItem, SearchAlbumAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean =
            oldItem == newItem

    }
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SearchAlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(val binding: SearchAlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
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