package com.abubakar.features.service

import com.abubakar.features.details.*
import com.abubakar.features.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie")
    suspend fun getLatestMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("release_date.lte") releaseData: String
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCastInfo(@Path("movie_id") movieId: Int): MovieCastResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") keyword: String,
        @Query("page") page: Int
    ): MoviesResponse

}