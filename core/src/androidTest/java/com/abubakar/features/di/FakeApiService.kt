package com.abubakar.features.di

import com.abubakar.features.details.Genres
import com.abubakar.features.details.MovieCastInfo
import com.abubakar.features.details.MovieCastResponse
import com.abubakar.features.details.MovieResponse
import com.abubakar.features.models.ImageUrl
import com.abubakar.features.models.ImageUrl.BannerImage
import com.abubakar.features.models.ImageUrl.PosterImage
import com.abubakar.features.models.Movie
import com.abubakar.features.models.MoviesResponse
import com.abubakar.features.service.ApiService


class FakeApiService : ApiService {

    override suspend fun getLatestMovies(
        page: Int,
        sortBy: String,
        releaseData: String
    ): MoviesResponse = MoviesResponse(
        page = 1,
        totalPages = 1,
        totalResults = 1,
        movies = listOf(
            Movie(
                adult = false,
                overview = "A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?",
                releaseDate = "1994-06-23",
                id = 8587,
                title = "The Lion King",
                poster = PosterImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                banner = BannerImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                voteCount = 14578,
                voteAverage = 8.3F
            )
        )
    )

    override suspend fun getMovieDetail(movieId: Int): MovieResponse =
        MovieResponse(
            adult = false,
            genres = listOf(
                Genres(18, "Drama")
            ),
            id = 550,
            title = "The Lion King",
            overview = "A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?",
            releaseDate = "1994-06-23",
            banner = BannerImage("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg"),
            poster = PosterImage("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg"),
            duration = 139,
            voteAverage = 7.8F,
            voteCount = 3439,
        )

    override suspend fun getMovieCastInfo(movieId: Int): MovieCastResponse {
        return MovieCastResponse(
            id = 1,
            cast = listOf(
                MovieCastInfo(
                    1,
                    "Edward Norton",
                    ImageUrl.ProfileImage("/5XBzD5WuTyVQZeS4VI25z2moMeY.jpg")
                )
            )
        )
    }

    override suspend fun searchMovie(keyword: String, page: Int): MoviesResponse = MoviesResponse(
        page = 1,
        totalPages = 1,
        totalResults = 1,
        movies = listOf(
            Movie(
                adult = false,
                title = "The Lion King",
                overview = "A young lion prince is cast out of his pride by his cruel uncle, who claims he killed his father. While the uncle rules with an iron paw, the prince grows up beyond the Savannah, living by a philosophy: No worries for the rest of your days. But when his past comes to haunt him, the young prince must decide his fate: Will he remain an outcast or face his demons and become what he needs to be?",
                releaseDate = "1994-06-23",
                id = 8587,
                poster = PosterImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                banner = BannerImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                voteCount = 14578,
                voteAverage = 8.3F
            ),

            Movie(
                adult = false,
                overview = "The King of Fighters movie will introduce a new science fiction spin into the setting established in the games universe by following the surviving members of three legendary fighting clans who are continually whisked away to other dimensions by an evil power. As the fighters enter each new world they battle that universes native defenders, while the force that brought them seeks to find a way to invade and infect our world.",
                releaseDate = "2010-08-26",
                id = 44571,
                title = "The King of Fighters",
                poster = PosterImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                banner = BannerImage("/sKCr78MXSLixwmZ8DyJLrpMsd15.jpg"),
                voteCount = 89,
                voteAverage = 4.7F
            )
        )
    )

}