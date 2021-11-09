package com.abubakar.features.util.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toFormat(format: String, default: String = ""): String = runCatching {
    SimpleDateFormat(format).format(this)
}.getOrDefault(default)
