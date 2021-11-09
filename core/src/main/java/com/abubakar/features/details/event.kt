package com.abubakar.features.details

import com.abubakar.base.Event
import com.abubakar.features.event.EventName.on_tap_add_movie_to_list
import com.abubakar.features.event.EventName.on_tap_share_movie

fun OnTapAddMovieToList(item: MovieDetail) = Event(
    on_tap_add_movie_to_list,
    mapOf(
        "screen_name" to "Detail Screen",
        "movie_name" to item.title
    )
)

fun OnTapMovieShare(item: MovieDetail) = Event(
    on_tap_share_movie,
    mapOf(
        "screen_name" to "Detail Screen",
        "movie_name" to item.title
    )
)