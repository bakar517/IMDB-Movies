package com.abubakar.features.util.ext

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import com.abubakar.features.R
import com.abubakar.features.di.GlideApp
import com.abubakar.features.models.ImageUrl
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey

fun ImageView.loadImage(
    imageUrl: ImageUrl?,
    progress: ProgressBar? = null,
) = imageUrl?.let { loadImage(imageUrl.url, progress) } ?: showErrorImage()

private fun ImageView.showErrorImage() {
    this.setImageResource(R.drawable.ic_image_24)
}

private fun ImageView.loadImage(
    path: String,
    progress: ProgressBar? = null,
    @DrawableRes ic_error: Int = R.drawable.ic_image_24
) = loadImage(
    path, RequestOptions().error(ic_error),
    object : RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            progress?.hide()
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progress?.hide()
            return false
        }
    }
)

private fun ImageView.loadImage(
    path: String,
    options: RequestOptions,
    listener: RequestListener<Bitmap>?
) = GlideApp.with(this.context)
    .asBitmap()
    .load(path)
    .apply(options)
    .diskCacheStrategy(DiskCacheStrategy.DATA)
    .signature(ObjectKey(path))
    .listener(listener)
    .into(this)

