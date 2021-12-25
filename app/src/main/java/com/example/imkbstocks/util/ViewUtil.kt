package com.example.imkbstocks.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.imkbstocks.R
import com.example.imkbstocks.mResources
import com.example.imkbstocks.model.ListModel
import com.example.imkbstocks.model.detailmodelpackage.DetailModel

fun showErrorMessage(context: Context?) {
    Toast.makeText(context, "Hata", Toast.LENGTH_LONG).show()
}

fun getStockStatusImage(stock : ListModel.Stock) : Drawable? {
    stock.isUp?.let { if (it) return mResources?.getDrawable(R.drawable.up_arrow)}
    stock.isDown?.let { if (it) return mResources?.getDrawable(R.drawable.down_arrow)}
    return mResources?.getDrawable(R.drawable.none)
}

fun getDetailStatusImage(detail : DetailModel?) : Drawable? {
    detail?.isUp?.let { if (it) return mResources?.getDrawable(R.drawable.up_arrow)}
    detail?.isDown?.let { if (it) return mResources?.getDrawable(R.drawable.down_arrow)}
    return mResources?.getDrawable(R.drawable.none)
}

fun getBackgroundColor(pos: Int) : Int?{
    return if (pos % 2 == 0) mResources?.getColor(R.color.white)
    else mResources?.getColor(R.color.gray)
}