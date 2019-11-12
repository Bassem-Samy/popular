package com.github.popular.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

interface ImageLoader {
    fun loadImage(imageView: ImageView, imageUrl: String, @DrawableRes placeHolderId: Int)
    fun loadImageCircular(imageView: ImageView, imageUrl: String, @DrawableRes placeHolderId: Int)
    fun loadDrawable(imageView: ImageView, @DrawableRes placeHolderId: Int)
}

class GlideImageLoader : ImageLoader {
    override fun loadImageCircular(imageView: ImageView, imageUrl: String, @DrawableRes placeHolderId: Int) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeHolderId)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    override fun loadDrawable(imageView: ImageView, drawableId: Int) {
        Glide.with(imageView.context)
            .load(drawableId)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }

    override fun loadImage(imageView: ImageView, imageUrl: String, @DrawableRes placeHolderId: Int) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(placeHolderId)
            .into(imageView)
    }
}