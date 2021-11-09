package com.abubakar.features.util.ext

import android.view.View
import androidx.viewbinding.ViewBinding

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showOrHide(show: Boolean) = if (show) visible() else hide()

fun ViewBinding.hideAll(vararg views: View) {
    views.forEach { it.hide() }
}
