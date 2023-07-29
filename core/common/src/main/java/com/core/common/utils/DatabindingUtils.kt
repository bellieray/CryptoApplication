package com.core.common.utils

import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object DatabindingUtils {
    @JvmStatic
    @BindingAdapter(value = ["app:imageUrl", "app:placeHolderDrawable"], requireAll = false)
    fun AppCompatImageView.loadImageWithPlaceholderDrawable(
        imageUrl: String?,
        imageDrawable: Drawable
    ) {
        Glide.with(this.context).load(imageUrl).placeholder(imageDrawable).error(imageDrawable)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("app:textColorBasedOnPrefix")
    fun setTextColorBasedOnPrefix(view: AppCompatTextView, text: String?) {
        if (text != null && text.startsWith("-")) {
            view.setTextColor(ContextCompat.getColor(view.context, android.R.color.holo_red_dark))
        } else {
            view.setTextColor(ContextCompat.getColor(view.context, android.R.color.holo_green_dark))
        }
        view.text = text
    }
}