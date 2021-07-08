package com.example.rickandmorty.extensions

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @author Samuel da Costa Araujo Nunes
 * Created 03/07/2021 at 02:04
 */

@BindingAdapter("visibleIf")
fun View.visibleIf(condicao: Boolean) {
    visibility = if (condicao) {
        View.VISIBLE
    } else {
        View.GONE
    }
}