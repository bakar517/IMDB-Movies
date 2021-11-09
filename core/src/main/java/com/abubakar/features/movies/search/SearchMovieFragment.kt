package com.abubakar.features.movies.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abubakar.features.R
import com.abubakar.features.databinding.FragmentSearchMovieBinding
import com.abubakar.features.movies.LoadingErrorStateAdapter
import com.abubakar.features.movies.MovieListAdapter
import com.abubakar.features.movies.discovery.bindLoadingErrorState
import com.abubakar.features.movies.discovery.getViewModel
import com.abubakar.features.util.RecyclerViewItemDecoration
import com.abubakar.features.util.ext.dismissKeyboard
import com.abubakar.features.util.ext.latest
import com.abubakar.features.util.ext.showOrHide
import com.abubakar.features.util.ext.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMovieFragment @Inject constructor(
    private val factory: ViewModelProvider.Factory
) : Fragment() {

    private val viewModel: SearchMovieViewModel by lazy { getViewModel(factory) }
    private val searchAdapter by lazy {
        MovieListAdapter(showPromoBanner = false) {
            viewModel.state.latest.onItemClick(it)
        }
    }
    private val loadingErrorStateAdapter by lazy { LoadingErrorStateAdapter() }
    private lateinit var binding: FragmentSearchMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = viewBinding(R.layout.fragment_search_movie, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview()

        binding.inputSearchMovies.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.inputSearchMovies.dismissKeyboard()
                performSearch(binding.inputSearchMovies.text.toString())
                true
            } else false
        }

        lifecycleScope.launch {

            viewModel.movies.collect {
                searchAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {

            searchAdapter.bindLoadingErrorState(
                onLoading = {
                    binding.progress.showOrHide(it)
                }, onError = {
                    binding.lblError.showOrHide(it)
                })
        }
    }

    private fun performSearch(query: String) = viewModel.searchMovie(query)

    private fun setupRecyclerview() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter.withLoadStateFooter(loadingErrorStateAdapter)
            overScrollMode = OVER_SCROLL_NEVER
            addItemDecoration(
                RecyclerViewItemDecoration(
                    resources.getDimension(R.dimen.spacing_micro).toInt()
                )
            )
        }
    }
}