package com.abubakar.features.movies.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abubakar.features.models.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val factory: SearchSource.Factory,
) {
    fun searchMovies(query: String): Flow<PagingData<Movie>> = Pager(
        config = SearchPagingConfig(),
        pagingSourceFactory = { factory.create(query) }
    ).flow
}