package com.abubakar.features.details

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MovieDetailsArgs(
    val id: Int,
    val title:String
): Parcelable
