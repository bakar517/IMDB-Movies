package com.abubakar.features.movies.discovery

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abubakar.base.ErrorLogger
import com.abubakar.features.models.Movie
import com.abubakar.features.service.IMDBService
import com.abubakar.features.util.ext.toFormat
import java.util.*
import javax.inject.Inject

private const val START_PAGE_INDEX = 1
private const val PAGE_SIZE = 15
private const val PREFETCH_DISTANCE = 5

private const val SortBy = "release_date.desc"
private const val ReleaseDateFormat = "YYYY-MM-dd"

class DiscoverySource @Inject constructor(
    private val service: IMDBService,
    private val errorLog: ErrorLogger,
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
            val movies = service.getLatestMovies(page, SortBy, Date().toFormat(ReleaseDateFormat))

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
}

fun DiscoveryPagingConfig() = PagingConfig(
    pageSize = PAGE_SIZE,
    prefetchDistance = PREFETCH_DISTANCE
)