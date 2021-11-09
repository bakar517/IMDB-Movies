package com.abubakar.features.movies.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.abubakar.base.EventLogger
import com.abubakar.base.Navigator
import com.abubakar.features.movies.MovieItem
import com.abubakar.features.movies.OnTapMovieDetail
import com.abubakar.features.movies.goToMovieDetailFromSearchScreen
import com.abubakar.features.movies.toMovieItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMovieViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val eventLogger: EventLogger,
    private val navigator: Navigator,
) : ViewModel() {

    private val _state = MutableLiveData(SearchViewState {
        eventLogger.log(OnTapMovieDetail())
        navigator.goToMovieDetailFromSearchScreen(it)
    })

    val state: LiveData<SearchViewState>
        get() = _state

    private val searches = MutableSharedFlow<String>()

    val movies = searches
        .distinctUntilChanged()
        .flatMapLatest { repository.searchMovies(it) }
        .map {
            it.map { movie -> movie.toMovieItem() }
        }.cachedIn(viewModelScope)


    fun searchMovie(query: String) {
        viewModelScope.launch {
            searches.emit(query)
        }
    }
}

data class SearchViewState(
    val onItemClick: (item: MovieItem) -> Unit,
)