package com.monzo.androidtest.common.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.monzo.androidtest.R

@BindingAdapter(
        requireAll = false,
        value = [
            "imageUrl",
            "circleCrop"
        ]
)
fun ImageView.setImageUrl(url: String?, circleCrop: Boolean? = false) {

    val glideRequest = Glide.with(this)
            .load(url)
            .error(R.color.lightGrey)
            .transition(DrawableTransitionOptions.withCrossFade())

    if (circleCrop == true) glideRequest.circleCrop()

    glideRequest.into(this)

}