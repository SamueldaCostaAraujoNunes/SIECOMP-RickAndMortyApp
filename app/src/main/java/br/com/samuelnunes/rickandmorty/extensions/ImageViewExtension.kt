package br.com.samuelnunes.rickandmorty.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.samuelnunes.rickandmorty.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 02/07/2021 at 21:54
 */

@BindingAdapter("url")
fun ImageView.imageUrl(url: String?) {
    val cornerRadius = context.resources.getDimensionPixelSize(R.dimen.corner_radius)
    Glide.with(context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(cornerRadius)))
        .into(this)
}