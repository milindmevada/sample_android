package com.example.milind.shareddata.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity() {
    open val mCompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
        mCompositeDisposable.dispose()
    }
}