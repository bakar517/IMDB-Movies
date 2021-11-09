package com.abubakar.features.util.ext

fun Int.toTimeFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}m"
}