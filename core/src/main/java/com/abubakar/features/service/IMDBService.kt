package com.abubakar.features.service

import com.abubakar.features.di.Dispatchers
import kotlinx.coroutines.invoke
import javax.inject.Inject

class IMDBService @Inject constructor(
    private val dispatchers: Dispatchers,
    private val apiService: ApiService,
) {

    suspend fun getLatestMovies(page: Int, sortBy: String, releaseData: String) = dispatchers.IO {
        apiService.getLatestMovies(page, sortBy, releaseData)
    }

    suspend fun getMovieDetail(movieId: Int) = dispatchers.IO {
        apiService.getMovieDetail(movieId)
    }

    suspend fun getMovieCastInfo(movieId: Int) = dispatchers.IO {
        apiService.getMovieCastInfo(movieId)
    }

    suspend fun searchMovie(query: String, page: Int) = dispatchers.IO {
        apiService.searchMovie(query, page)
    }

}