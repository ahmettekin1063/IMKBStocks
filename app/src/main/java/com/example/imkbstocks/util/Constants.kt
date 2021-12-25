package com.example.imkbstocks

import android.content.res.Resources
import android.os.Build
import android.view.View.GONE
import android.view.View.VISIBLE
import java.util.*

enum class HandshakeStatus(val pbHandshakeVisibility: Int, val btnLoginVisibility: Int) {
    IN_PROGRESS(VISIBLE, GONE),
    DONE(GONE, VISIBLE),
    FAILURE(GONE, GONE)
}

enum class StockListStatus(val pbStockListVisibility: Int, val rvStockListVisibility: Int) {
    PREPARING(VISIBLE, GONE),
    READY(GONE, VISIBLE),
    FAILURE(GONE, GONE)
}

enum class DetailResponseStatus(val pbDetailChartVisibility: Int, val detailChartVisibility: Int) {
    PREPARING(VISIBLE, GONE),
    READY(GONE, VISIBLE),
    FAILURE(GONE, GONE)
}

enum class Periods(val period: String) {
    ALL("all"),
    INCREASING("increasing"),
    DECREASING("decreasing"),
    VOLUME_30("volume30"),
    VOLUME_50("volume50"),
    VOLUME_100("volume100")
}

internal const val BASE_URL = "https://mobilechallenge.veripark.com/"
internal const val PLATFORM_NAME = "Android"
internal const val AES_KEY = "AES_KEY"
internal const val AES_IV = "AES_IV"
internal const val AUTH = "AUTH"
internal const val ALGORITHM = "AES/CBC/PKCS7Padding"

internal var mResources: Resources? = null

internal val systemVersion = Build.VERSION.RELEASE
internal val deviceModel = Build.MODEL
internal val manufacturer = Build.MANUFACTURER
internal val deviceId = UUID.randomUUID().toString()
internal val handshakeMap = HashMap<String, String?>()
