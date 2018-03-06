package com.example.milind.commons.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context?.isInternetAvailable(): Boolean {
    return try {
        val connectivityManager = this?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo != null &&
                (connectivityManager.activeNetworkInfo.isConnected)
    } catch (ex: Exception) {
        ex.printStackTrace()
        false
    }
}

fun Context.hideKeyboard(view: View?) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
    imm.showSoftInput(view, 0)
}
