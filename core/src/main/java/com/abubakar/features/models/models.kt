package com.abubakar.features.models

import com.abubakar.features.models.ImageUrl.BannerImage
import com.abubakar.features.models.ImageUrl.PosterImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias JsonKey = Json

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    @JsonKey("page") val page: Int,
    @JsonKey("total_pages") val totalPages: Int,
    @JsonKey("total_results") val totalResults: Int,
    @JsonKey("results") val movies: List<Movie>
)


@JsonClass(generateAdapter = true)
data class Movie(
    @JsonKey("id") val id: Int,
    @JsonKey("title") val title: String,
    @JsonKey("overview") val overview: String,
    @JsonKey("adult") val adult: Boolean,
    @JsonKey("poster_path") val poster: PosterImage?,
    @JsonKey("backdrop_path") val banner: BannerImage?,
    @JsonKey("release_date") val releaseDate: String,
    @JsonKey("vote_average") val voteAverage: Float,
    @JsonKey("vote_count") val voteCount: Int,
)
