package com.example.milind.injection.component

import com.example.milind.injection.module.RetrofitModule
import com.example.milind.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(RetrofitModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
}