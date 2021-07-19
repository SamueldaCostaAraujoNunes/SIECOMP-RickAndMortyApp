package br.com.samuelnunes.rickandmorty.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 02/07/2021 at 21:54
 */

@BindingAdapter("url")
fun ImageView.imageUrl(url: String?) =
    Glide.with(context).load(url).circleCrop().into(this)
