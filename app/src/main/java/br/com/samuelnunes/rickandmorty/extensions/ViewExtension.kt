package br.com.samuelnunes.rickandmorty.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 29/07/2021 at 11:58
 */
@BindingAdapter("visibleIf")
fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) {
        VISIBLE
    } else {
        GONE
    }
}