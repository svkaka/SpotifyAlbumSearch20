package com.ovrbach.mvolvochallenge.app.feature.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ovrbach.mvolvochallenge.databinding.AlbumDetailsTrackItemBinding
import com.ovrbach.mvolvochallenge.model.entity.Track

class AlbumTracksAdapter : ListAdapter<Track, AlbumTracksAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
            oldItem == newItem

    }
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            AlbumDetailsTrackItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: AlbumDetailsTrackItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Track) {
            with(binding) {
                artists.text = item.artists.joinToString { it.name }
                name.text = item.name
                duration.text = item.durationsString
                number.text = String.format("#%02d", item.trackNumber)
            }
        }

    }
}