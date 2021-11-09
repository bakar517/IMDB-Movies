package com.abubakar.features.movies.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abubakar.features.R
import com.abubakar.features.databinding.FragmentDiscoveryBinding
import com.abubakar.features.movies.LoadingErrorStateAdapter
import com.abubakar.features.movies.MovieItem
import com.abubakar.features.movies.MovieListAdapter
import com.abubakar.features.util.RecyclerViewItemDecoration
import com.abubakar.features.util.ext.latest
import com.abubakar.features.util.ext.showOrHide
import com.abubakar.features.util.ext.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class DiscoveryFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel: DiscoveryViewModel by lazy { getViewModel(factory) }
    private val moviesAdapter by lazy { MovieListAdapter { viewModel.state.latest.onItemClick(it) } }
    private val loadingErrorStateAdapter by lazy { LoadingErrorStateAdapter() }
    private lateinit var binding: FragmentDiscoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(R.layout.fragment_discovery, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.setupRecyclerview()
        binding.setupToolbarMenu()
        binding.setupLoadingErrorView()

        lifecycleScope.launch {
            viewModel.movies.collect {
                moviesAdapter.submitData(it)
            }
        }
    }

    private fun FragmentDiscoveryBinding.setupLoadingErrorView() {
        lifecycleScope.launch {
            moviesAdapter.bindLoadingErrorState(
                onLoading = {
                    progress.showOrHide(it)
                },
                onError = {
                    lblError.showOrHide(it)
                })
        }
    }

    private fun FragmentDiscoveryBinding.setupRecyclerview() {
        list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter.withLoadStateFooter(loadingErrorStateAdapter)
            overScrollMode = OVER_SCROLL_NEVER
            addItemDecoration(
                RecyclerViewItemDecoration(
                    resources.getDimension(R.dimen.spacing_micro).toInt()
                )
            )
        }
    }

    private fun FragmentDiscoveryBinding.setupToolbarMenu() {
        toolbarLayout.toolbar.inflateMenu(R.menu.search_menu)
        toolbarLayout.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    viewModel.state.latest.onSearchClick()
                    true
                }
                else -> false
            }
        }
    }
}

suspend fun PagingDataAdapter<MovieItem, RecyclerView.ViewHolder>.bindLoadingErrorState(
    onLoading: (Boolean) -> Unit,
    onError: (Boolean) -> Unit,
) {
    loadStateFlow.collect { loadState ->
        onLoading(loadState.source.refresh is LoadState.Loading && itemCount == 0)
        onError(loadState.source.refresh is LoadState.Error && itemCount == 0)
    }
}

inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory): T =
    ViewModelProvider(this, factory)[T::class.java]