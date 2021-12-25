package com.example.imkbstocks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.imkbstocks.DetailResponseStatus
import com.example.imkbstocks.StockListStatus
import com.example.imkbstocks.model.ListModel
import com.example.imkbstocks.model.detailmodelpackage.DetailModel
import io.reactivex.disposables.CompositeDisposable

class StockDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    val detail = MutableLiveData<DetailModel>()
    val detailResponseStatus = MutableLiveData<DetailResponseStatus>()

    fun getDetail(stockId: Int?) {

    }
}
