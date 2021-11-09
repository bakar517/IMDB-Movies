package com.abubakar.features.movies.search

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abubakar.base.ErrorLogger
import com.abubakar.features.models.Movie
import com.abubakar.features.service.IMDBService
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

private const val START_PAGE_INDEX = 1
private const val PAGE_SIZE = 15
private const val PREFETCH_DISTANCE = 5

class SearchSource @AssistedInject constructor(
    private val service: IMDBService,
    private val errorLog: ErrorLogger,
    @Assisted private val query: String,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> =
        runCatching {
            val page = params.key ?: START_PAGE_INDEX
            val movies = service.searchMovie(query, page)
            val prevKey = if (page == START_PAGE_INDEX) null else page.minus(1)
            val nextKey =
                if (movies.totalPages - movies.page > 0) movies.page.plus(1) else null
            LoadResult.Page(
                data = movies.movies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }.getOrElse {
            errorLog.log(it)
            LoadResult.Error(it)
        }

    @AssistedFactory
    interface Factory {
        fun create(query: String): SearchSource
    }
}

fun SearchPagingConfig() = PagingConfig(
    pageSize = PAGE_SIZE,
    prefetchDistance = PREFETCH_DISTANCE
)