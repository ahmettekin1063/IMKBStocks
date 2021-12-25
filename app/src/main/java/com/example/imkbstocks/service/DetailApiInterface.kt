package com.example.imkbstocks.service

import com.example.imkbstocks.model.DetailRequestModel
import com.example.imkbstocks.model.detailmodelpackage.DetailModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface DetailApiInterface {

    @Headers("Content-Type: application/json")
    @POST("api/stocks/detail")
    fun getDetail(@Body id: DetailRequestModel, @Header("X-VP-Authorization") authorization: String): Observable<DetailModel>
}