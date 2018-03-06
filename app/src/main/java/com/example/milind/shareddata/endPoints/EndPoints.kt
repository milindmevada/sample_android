package com.example.milind.shareddata.endPoints

import com.example.milind.shareddata.model.GlobalResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface EndPoints {
    @GET("/")
    fun sendMessage(@Query("name") name: String): Observable<GlobalResponse>
}