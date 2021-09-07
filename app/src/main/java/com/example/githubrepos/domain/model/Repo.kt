package com.example.githubrepos.domain.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class Repo(var id: Int, var name: String, var description: String, var owner: Owner) {

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String) = Glide.with(view.context).load(url).into(view)
    }
}
