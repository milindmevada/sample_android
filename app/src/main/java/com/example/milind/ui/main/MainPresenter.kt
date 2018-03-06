package com.example.milind.ui.main

import android.content.Context
import com.example.milind.R
import com.example.milind.commons.extensions.isInternetAvailable
import com.example.milind.shareddata.endPoints.EndPoints
import com.example.milind.shareddata.endPoints.ResponseCode
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mApi: EndPoints) {

    lateinit var mView: MainView

    fun injectView(view: MainView) {
        this.mView = view
    }

    fun sendMessage(message: String) {

        if (!mView.mContext.isInternetAvailable()) {
            mView.internetNotAvailable()
            return
        }

        if (message.isEmpty()) {
            mView.invalidMessage()
            return
        }

        mApi.sendMessage(message)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mView.changeLoading(true) }
                .subscribeBy(
                        onNext = {
                            mView.changeLoading(false)
                            when (it.status) {
                                ResponseCode.SUCCESS -> {
                                    mView.showMessage(mView.mContext.getString(R.string.msg_200))
                                }
                                ResponseCode.NOT_FOUND -> {
                                    mView.showMessage(mView.mContext.getString(R.string.msg_404))
                                }
                                ResponseCode.UNAUTHORIZED_ACCESS -> {
                                    mView.showMessage(mView.mContext.getString(R.string.msg_403))
                                }
                                ResponseCode.INTERNAL_UNKNOWN_EXCEPTION -> {
                                    mView.showMessage(mView.mContext.getString(R.string.msg_500))
                                }
                                else -> {
                                    mView.showMessage(mView.mContext.getString(R.string.msg_422))
                                }
                            }
                        },
                        onError = {
                            it.printStackTrace()
                            mView.showError()
                            mView.changeLoading(false)
                        }
                ).addTo(mView.mCompositeDisposable)
    }

    interface MainView {
        val mContext: Context

        val mCompositeDisposable: CompositeDisposable

        fun showMessage(value: String)

        fun showError()

        fun internetNotAvailable()

        fun changeLoading(isLoading: Boolean)

        fun invalidMessage()
    }
}