package com.abubakar.features.movies

import com.abubakar.features.models.ImageUrl.BannerImage
import com.abubakar.features.models.ImageUrl.PosterImage
import com.abubakar.features.models.Movie

data class MovieItem(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: PosterImage?,
    val banner: BannerImage?,
    val releaseDate: String,
    val voteAverage: Float,
)

fun Movie.toMovieItem() = MovieItem(
    id = id,
    title = title,
    overview = overview,
    poster = poster,
    banner = banner,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)
