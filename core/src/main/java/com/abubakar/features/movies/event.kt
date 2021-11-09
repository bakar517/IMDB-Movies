package com.abubakar.features.movies

import com.abubakar.base.Event
import com.abubakar.features.event.EventName.on_tap_movie_detail

fun OnTapMovieDetail() = Event(
    on_tap_movie_detail,
    mapOf(
        "screen_name" to "Home"
    )
)