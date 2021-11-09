package com.abubakar.features.details

import com.abubakar.features.models.ImageUrl.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias JsonKey = Json

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @JsonKey("adult") val adult: Boolean,
    @JsonKey("backdrop_path") val banner: BannerImage?,
    @JsonKey("genres") val genres: List<Genres>,
    @JsonKey("id") val id: Int,
    @JsonKey("overview") val overview: String?,
    @JsonKey("poster_path") val poster: PosterImage?,
    @JsonKey("release_date") val releaseDate: String,
    @JsonKey("runtime") val duration: Int?,
    @JsonKey("title") val title: String,
    @JsonKey("vote_average") val voteAverage: Float,
    @JsonKey("vote_count") val voteCount: Int,
)

@JsonClass(generateAdapter = true)
data class Genres(
    @JsonKey("id") val id: Int,
    @JsonKey("name") val name: String,
)

@JsonClass(generateAdapter = true)
data class MovieCastResponse(
    @JsonKey("id") val id: Int,
    @JsonKey("cast") val cast: List<MovieCastInfo>,
)

@JsonClass(generateAdapter = true)
data class MovieCastInfo(
    @JsonKey("id") val id: Int,
    @JsonKey("name") val name: String,
    @JsonKey("profile_path") val profileImage: ProfileImage?,
)

