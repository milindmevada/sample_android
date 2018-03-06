package com.example.milind.commons.extensions

import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View

fun View.snack(@StringRes msg: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, context.getString(msg), duration).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}