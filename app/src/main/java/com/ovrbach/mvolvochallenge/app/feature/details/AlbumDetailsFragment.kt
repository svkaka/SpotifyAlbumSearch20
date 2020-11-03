package com.ovrbach.mvolvochallenge.app.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ovrbach.mvolvochallenge.R
import com.ovrbach.mvolvochallenge.databinding.AlbumDetailsFragmentBinding
import com.ovrbach.mvolvochallenge.model.entity.AlbumDetails
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailsFragment : BottomSheetDialogFragment() {

    companion object {
        const val ALBUM_ITEM = "com.ovrbach.mvolvochallenge.model.entity.ALBUM_ITEM"
        fun newInstance(albumItem: AlbumItem) = AlbumDetailsFragment().apply {
            arguments = bundleOf(ALBUM_ITEM to albumItem)
        }
    }

    private val albumDetailsViewModel: AlbumDetailsViewModel by viewModels()
    private val tracksAdapter = AlbumTracksAdapter()

    private lateinit var binding: AlbumDetailsFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = AlbumDetailsFragmentBinding.inflate(inflater).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumDetailsViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is AlbumDetailsViewModel.State.PartiallyLoaded -> binding.showPartialView(state.albumItem)
                is AlbumDetailsViewModel.State.Loaded -> binding.showDetails(state.albumDetails)
                is AlbumDetailsViewModel.State.Failed -> binding.showError(state)
            }

        })
        with(binding) {
            list.adapter = tracksAdapter
            list.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun AlbumDetailsFragmentBinding.showPartialView(item: AlbumItem) {
        error.isVisible =false
        artists.text = item.artists.joinToString { it.name }
        name.text = item.name
        releaseDate.text = item.releaseDate
        image.load(item.images.firstOrNull()?.url)
    }

    private fun AlbumDetailsFragmentBinding.showDetails(details: AlbumDetails) {
        error.isVisible =false
        progress.isVisible = false
        artists.text = details.artists.joinToString { it.name }
        name.text = details.name
        releaseDate.text = details.releaseDate
        image.load(details.images.firstOrNull()?.url)
        tracksAdapter.submitList(details.tracks)
    }

    private fun AlbumDetailsFragmentBinding.showError(state: AlbumDetailsViewModel.State.Failed) {
        error.isVisible =true
        progress.isVisible = false
        error.text = getString(R.string.details_error_title, state.throwable.message)
    }


}