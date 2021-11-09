package com.abubakar.features.models

import android.content.Context
import android.util.DisplayMetrics
import com.abubakar.features.models.ImageUrl.*
import com.squareup.moshi.*
import java.lang.reflect.Type

private const val BaseImageUrl = "https://image.tmdb.org/t/p/"

sealed class ImageUrl(val url: String) {
    data class PosterImage(val imageUrl: String) : ImageUrl(imageUrl)

    data class BannerImage(val imageUrl: String) : ImageUrl(imageUrl)

    data class ProfileImage(val imageUrl: String) : ImageUrl(imageUrl)
}

class ImageUrlJsonAdapter(private val creator: (String?) -> ImageUrl) : JsonAdapter<ImageUrl>() {

    override fun fromJson(reader: JsonReader) = creator(
        if (reader.peek() != JsonReader.Token.NULL) {
            reader.nextString()
        } else {
            reader.nextNull<Unit>()
            ""
        }
    )

    override fun toJson(writer: JsonWriter, value: ImageUrl?) = error("Invalid operation!!")

    class Factory(val context: Context) : JsonAdapter.Factory {
        override fun create(
            type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi
        ): JsonAdapter<*>? {
            return if (ImageUrl::class.java.isAssignableFrom(type.rawType)) {
                ImageUrlJsonAdapter(
                    creator = when (type.rawType) {
                        PosterImage::class.java -> ({
                            PosterImage("$BaseImageUrl${context.posterDirectory()}$it")
                        })
                        BannerImage::class.java -> ({
                            BannerImage("$BaseImageUrl${context.bannerDirectory()}$it")
                        })
                        ProfileImage::class.java -> ({
                            ProfileImage("$BaseImageUrl${context.profileDirectory()}$it")
                        })
                        else -> error("Invalid state!!")
                    }
                )
            } else null
        }
    }
}

private fun Context.bannerDirectory() = when (resources.displayMetrics.densityDpi) {
    in 0..DisplayMetrics.DENSITY_XHIGH -> "w780"
    in DisplayMetrics.DENSITY_XHIGH..DisplayMetrics.DENSITY_XXXHIGH -> "w1280"
    else -> error("unknown size")
}

private fun Context.posterDirectory() = when (resources.displayMetrics.densityDpi) {
    in 0..DisplayMetrics.DENSITY_HIGH -> "w185"
    in DisplayMetrics.DENSITY_HIGH..DisplayMetrics.DENSITY_XHIGH -> "w342"
    in DisplayMetrics.DENSITY_XHIGH..DisplayMetrics.DENSITY_XXHIGH -> "w500"
    in DisplayMetrics.DENSITY_XXHIGH..DisplayMetrics.DENSITY_XXXHIGH -> "w780"
    else -> error("unknown size")
}

private fun Context.profileDirectory() = when (resources.displayMetrics.densityDpi) {
    in 0..DisplayMetrics.DENSITY_XHIGH -> "w185"
    in DisplayMetrics.DENSITY_XHIGH..DisplayMetrics.DENSITY_XXXHIGH -> "h632"
    else -> error("unknown size")
}
