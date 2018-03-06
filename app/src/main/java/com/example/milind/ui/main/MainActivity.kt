package com.example.milind.ui.main

import android.content.Context
import android.os.Bundle
import com.example.milind.AppApplication
import com.example.milind.R
import com.example.milind.commons.extensions.gone
import com.example.milind.commons.extensions.hideKeyboard
import com.example.milind.commons.extensions.snack
import com.example.milind.commons.extensions.visible
import com.example.milind.shareddata.base.BaseActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity(), MainPresenter.MainView {

    override val mContext: Context
        get() = this

    @Inject lateinit var mPresenter: MainPresenter

    private var mContinuesTextChangeDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as AppApplication).mComponent.inject(this)
        mPresenter.injectView(this)
        handleUi()
    }

    override fun onDestroy() {
        super.onDestroy()
        mContinuesTextChangeDisposable?.dispose()
    }

    override fun invalidMessage() {
        this.currentFocus.snack(R.string.err_invalid_message)
    }

    override fun showMessage(value: String) {
        txtMessage.text = value
        changeTextAtInterval()
    }

    override fun showError() {
        this.currentFocus.snack(R.string.err_unknown)
    }

    override fun internetNotAvailable() {
        this.currentFocus.snack(R.string.err_no_internet)
    }

    override fun changeLoading(isLoading: Boolean) {
        if (isLoading) {
            progress.visible()
            txtMessage.gone()
        } else {
            txtMessage.visible()
            progress.gone()
        }

    }

    private fun handleUi() {
        btnSubmit.clicks().subscribe {
            hideKeyboard(btnSubmit)
            mPresenter.sendMessage(edtMessage.text.toString())
        }.addTo(mCompositeDisposable)

        edtMessage.textChanges().subscribe {
            mContinuesTextChangeDisposable?.dispose()
            txtMessage.text = ""
        }.addTo(mCompositeDisposable)
    }

    private fun changeTextAtInterval() {
        mContinuesTextChangeDisposable =
                Observable.interval(10, TimeUnit.SECONDS)
                        .take(1)
                        .timeInterval()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    mPresenter.sendMessage(edtMessage.text.toString())
                                },
                                onError = {
                                    it.printStackTrace()
                                }
                        )
    }
}
