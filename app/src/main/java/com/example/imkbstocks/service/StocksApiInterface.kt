package com.example.imkbstocks.service

import com.example.imkbstocks.model.ListModel
import com.example.imkbstocks.model.ListRequestModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface StocksApiInterface {

    @Headers("Content-Type: application/json")
    @POST("api/stocks/list")
    fun getStocks(@Body period: ListRequestModel, @Header("X-VP-Authorization") authorization: String?): Single<ListModel>
}