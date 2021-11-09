package com.abubakar.features.details

import com.abubakar.features.util.ext.toTimeFormat
import com.abubakar.features.util.ext.toYearFormat
import javax.inject.Inject

private const val ReleaseTimeFormat = "yyyy-MM-dd"
private const val RequireReleaseTimeFormat = "yyyy"
private const val Adult = 18
private const val Kind = 13

interface UIMapper {
    fun mapToMovieDetail(response: MovieResponse): MovieDetail

    fun mapToMovieCast(response: MovieCastResponse): List<MovieCast>
}

class UIMapperImpl @Inject constructor() : UIMapper {

    override fun mapToMovieDetail(response: MovieResponse) = MovieDetail(
        id = response.id,
        overview = response.overview,
        ageLimit = if (response.adult) Adult else Kind,
        banner = response.banner,
        poster = response.poster,
        genres = response.genres.map { it.name },
        duration = response.duration?.toTimeFormat() ?: "",
        releaseDate = response.releaseDate.toYearFormat(
            inputFormat = ReleaseTimeFormat,
            outputFormat = RequireReleaseTimeFormat
        ),
        title = response.title,
        voteAverage = response.voteAverage,
        voteCount = response.voteCount,
    )

    override fun mapToMovieCast(response: MovieCastResponse) = response.cast.map { cast ->
        MovieCast(id = cast.id, name = cast.name, image = cast.profileImage)
    }

}