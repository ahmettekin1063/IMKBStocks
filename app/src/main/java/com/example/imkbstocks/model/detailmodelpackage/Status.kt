package com.example.imkbstocks.model.detailmodelpackage

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Status(
    @SerializedName("error")
    var error: Error?,
    @SerializedName("isSuccess")
    var isSuccess: Boolean?
):Serializable