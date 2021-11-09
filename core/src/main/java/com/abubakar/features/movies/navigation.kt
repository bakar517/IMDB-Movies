package com.abubakar.features.movies

import com.abubakar.base.Navigator
import com.abubakar.features.details.MovieDetailsArgs
import com.abubakar.features.movies.discovery.DiscoveryFragmentDirections
import com.abubakar.features.movies.search.SearchMovieFragmentDirections


fun Navigator.goToMovieDetail(item: MovieItem) =
    navigate(DiscoveryFragmentDirections.actionGoToMovieDetailScreen(item.toNavArgs()))

fun Navigator.goToMovieDetailFromSearchScreen(item: MovieItem) =
    navigate(SearchMovieFragmentDirections.actionGoToMovieDetailScreen(item.toNavArgs()))

fun Navigator.goToSearchMovie() =
    navigate(DiscoveryFragmentDirections.actionGoToSearchMovieScreen())


private fun MovieItem.toNavArgs() = MovieDetailsArgs(
    id = id,
    title = title
)