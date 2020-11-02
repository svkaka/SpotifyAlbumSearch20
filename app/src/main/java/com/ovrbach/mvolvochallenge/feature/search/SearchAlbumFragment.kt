package com.ovrbach.mvolvochallenge.feature.search

import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ovrbach.mvolvochallenge.R
import com.ovrbach.mvolvochallenge.core.BaseViewFragment
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumFragmentBinding
import com.ovrbach.mvolvochallenge.feature.details.AlbumDetailsFragment
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAlbumFragment : BaseViewFragment<SearchAlbumFragmentBinding>(
    R.layout.search_album_fragment
) {

    private val searchViewModel: SearchAlbumViewModel by viewModels()

    private val searchAlbumAdapter =
        SearchAlbumAdapter {
            searchViewModel.onItemClick(it)
        }

    override fun bindView(view: View): SearchAlbumFragmentBinding =
        SearchAlbumFragmentBinding.bind(view)

    override fun SearchAlbumFragmentBinding.onViewCreated() {

        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = searchAlbumAdapter

        searchViewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                SearchAlbumViewModel.State.Empty -> showEmptyView()
                is SearchAlbumViewModel.State.Failed -> showFailedView(state)
                is SearchAlbumViewModel.State.Success -> showSuccessView(state)
            }
        })

        searchViewModel.sideEffects.observe(viewLifecycleOwner, Observer { sideEffect ->
            when (sideEffect) {
                SearchAlbumViewModel.SideEffect.Loading -> showProgress()
                null -> dismissProgress()
            }
        })

        searchViewModel.signal.observe(viewLifecycleOwner, Observer { signal ->
            when (signal) {
                is SearchAlbumViewModel.Signal.OpenDetails -> showDetailsFragment(signal.albumItem)
                null -> noop()
            }
        })

        editText.doAfterTextChanged { text ->
            searchViewModel.search(text?.toString())
        }

    }

    private fun SearchAlbumFragmentBinding.showEmptyView() {
        empty.visibility = View.VISIBLE
        searchAlbumAdapter.submitList(null)
    }

    private fun SearchAlbumFragmentBinding.showSuccessView(state: SearchAlbumViewModel.State.Success) {
        empty.visibility = View.GONE
        searchAlbumAdapter.submitList(state.items)
    }

    private fun SearchAlbumFragmentBinding.showFailedView(state: SearchAlbumViewModel.State.Failed) {
        empty.visibility = View.GONE
        searchAlbumAdapter.submitList(null)
        buildErrorSnackbar(state.throwable.message!!).show()
    }

    private fun SearchAlbumFragmentBinding.showProgress() {
        progress.visibility = View.VISIBLE
    }

    private fun SearchAlbumFragmentBinding.dismissProgress() {
        progress.visibility = View.GONE
    }

    private fun showDetailsFragment(item: AlbumItem) {
        AlbumDetailsFragment.newInstance(item)
            .show(parentFragmentManager, null)
    }

}

private inline fun noop() = Unit