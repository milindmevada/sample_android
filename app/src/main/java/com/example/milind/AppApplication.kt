package com.example.milind

import android.app.Application
import com.example.milind.injection.component.AppComponent
import com.example.milind.injection.component.DaggerAppComponent
import com.example.milind.injection.module.RetrofitModule

class AppApplication : Application() {
    lateinit var mComponent: AppComponent

    companion object {
        lateinit var instance: AppApplication

        fun getAppContext(): AppApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDagger()
    }

    private fun initDagger() {
        mComponent = DaggerAppComponent.builder()
                .retrofitModule(RetrofitModule())
                .build()
    }
}