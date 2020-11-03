package com.ovrbach.mvolvochallenge.app.feature.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var binding: AlbumDetailsFragmentBinding
    private var snackBar: Snackbar? = null

    private val tracksAdapter = AlbumTracksAdapter()

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
        val item = requireArguments().getParcelable<AlbumItem>(ALBUM_ITEM)!!
        with(binding) {
            list.adapter = tracksAdapter
            list.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun AlbumDetailsFragmentBinding.showPartialView(item: AlbumItem) {
        artists.text = item.artists.joinToString { it.name }
        name.text = item.name
        releaseDate.text = item.releaseDate
        image.load(item.images.firstOrNull()?.url)
    }

    private fun AlbumDetailsFragmentBinding.showDetails(details: AlbumDetails) {
        progress.visibility = View.GONE
        artists.text = details.artists.joinToString { it.name }
        name.text = details.name
        releaseDate.text = details.releaseDate
        image.load(details.images.firstOrNull()?.url)
        tracksAdapter.submitList(details.tracks)
    }

    private fun AlbumDetailsFragmentBinding.showError(state: AlbumDetailsViewModel.State.Failed) {
        progress.visibility = View.GONE
        buildErrorSnackbar(state.throwable.message!!)
    }

    private fun buildErrorSnackbar(
        message: CharSequence
    ): Snackbar {
        dismissSnackbar()

        return Snackbar.make(
            binding.root, message,
            Snackbar.LENGTH_INDEFINITE
        ).also { snack ->
            snack.setAction("Ok") {
                dismissSnackbar()
            }
            snackBar = snack
        }
    }

    private fun dismissSnackbar() {
        snackBar?.dismiss()
        snackBar = null
    }


}