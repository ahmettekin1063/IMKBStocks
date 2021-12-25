package com.example.imkbstocks.service

import com.example.imkbstocks.model.HandshakeModel
import com.example.imkbstocks.model.HandshakeRequestModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface HandshakeApiInterface {

    @Headers("Content-Type: application/json")
    @POST("api/handshake/start")
    fun getHandshake(@Body handshakeRequestModel: HandshakeRequestModel): Single<HandshakeModel>
}