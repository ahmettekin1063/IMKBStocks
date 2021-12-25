package com.example.imkbstocks.model


import com.google.gson.annotations.SerializedName

class ListRequestModel(
    @SerializedName("period")
    var period: String?
)