package com.abubakar.features.util.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toYearFormat(inputFormat: String, outputFormat: String, default: String = ""): String =
    runCatching { SimpleDateFormat(outputFormat).format(SimpleDateFormat(inputFormat).parse(this)!!) }
        .getOrDefault(default)

