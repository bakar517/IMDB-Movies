package com.abubakar.features.movies.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.abubakar.base.EventLogger
import com.abubakar.base.Navigator
import com.abubakar.features.movies.*
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscoveryViewModel @Inject constructor(
    private val source: DiscoverySource,
    private val navigator: Navigator,
    private val eventLogger: EventLogger,
) : ViewModel() {

    val movies = Pager(
        config = DiscoveryPagingConfig(),
        pagingSourceFactory = { source }
    ).flow.map {
        it.map { movie -> movie.toMovieItem() }
    }.cachedIn(viewModelScope)

    private val _state = MutableLiveData(ViewState(
        onItemClick = {
            eventLogger.log(OnTapMovieDetail())
            navigator.goToMovieDetail(it)
        },
        onSearchClick = {
            navigator.goToSearchMovie()
        }
    ))

    val state: LiveData<ViewState>
        get() = _state

}

data class ViewState(
    val onItemClick: (item: MovieItem) -> Unit,
    val onSearchClick: () -> Unit,
)
