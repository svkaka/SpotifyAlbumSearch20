package com.ovrbach.mvolvochallenge.app.feature.search

import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.ovrbach.mvolvochallenge.R
import com.ovrbach.mvolvochallenge.core.BaseViewFragment
import com.ovrbach.mvolvochallenge.databinding.SearchAlbumFragmentBinding
import com.ovrbach.mvolvochallenge.app.feature.details.AlbumDetailsFragment
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.core.util.noop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Error

@AndroidEntryPoint
class SearchAlbumFragment : BaseViewFragment<SearchAlbumFragmentBinding>(
        R.layout.search_album_fragment
) {

    private val searchViewModel: SearchAlbumViewModel by viewModels()

    private val searchAlbumAdapter = SearchAlbumAdapter { searchViewModel.onItemClick(it) }
    override fun bindView(view: View): SearchAlbumFragmentBinding =
            SearchAlbumFragmentBinding.bind(view)

    override fun SearchAlbumFragmentBinding.onViewCreated() {
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(requireContext())
        list.adapter = searchAlbumAdapter.withLoadStateFooter(
                SearchAlbumStateAdapter { searchAlbumAdapter.retry() }
        )

        searchAlbumAdapter.addLoadStateListener { combinedLoadStates ->
            val refreshState = combinedLoadStates.source.refresh
            if (refreshState is LoadState.Error) {
                buildErrorSnackbar(refreshState.error.message!!) { searchAlbumAdapter.retry() }.show()
            } else {
                dismissSnackbar()
            }
            empty.isVisible = combinedLoadStates.refresh is LoadState.NotLoading &&
                    searchAlbumAdapter.itemCount == 0
        }

        searchViewModel.signal.observe(viewLifecycleOwner, Observer { signal ->
            when (signal) {
                is SearchAlbumViewModel.Signal.OpenDetails -> showDetailsFragment(signal.albumItem)
                null -> noop()
            }
        })
        searchViewModel.searchEnabled.observe(viewLifecycleOwner, Observer { enable ->
            search.isEnabled = enable
        })

        searchViewModel.pages.observe(viewLifecycleOwner, Observer { data ->
            searchAlbumAdapter.submitData(lifecycle, data)
        })


        editText.doAfterTextChanged { text ->
            searchViewModel.onInputChanged(text?.toString())
        }

        search.setOnClickListener {
            searchViewModel.submitSearch(editText.text?.toString())
        }
    }

    private fun showDetailsFragment(item: AlbumItem) {
        AlbumDetailsFragment.newInstance(item)
                .show(parentFragmentManager, null)
    }

}