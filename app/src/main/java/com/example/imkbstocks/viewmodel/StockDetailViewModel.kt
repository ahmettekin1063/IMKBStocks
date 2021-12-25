package com.example.imkbstocks.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.imkbstocks.AUTH
import com.example.imkbstocks.DetailResponseStatus
import com.example.imkbstocks.StockListStatus
import com.example.imkbstocks.handshakeMap
import com.example.imkbstocks.model.DetailRequestModel
import com.example.imkbstocks.model.ListModel
import com.example.imkbstocks.model.detailmodelpackage.DetailModel
import com.example.imkbstocks.service.ApiClient
import com.example.imkbstocks.service.DetailApiInterface
import com.example.imkbstocks.service.StocksApiInterface
import com.example.imkbstocks.util.encrypt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class StockDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    val detail = MutableLiveData<DetailModel>()
    val detailResponseStatus = MutableLiveData<DetailResponseStatus>()

    fun getDetailData(stockId: Int?) {
        detailResponseStatus.value = DetailResponseStatus.PREPARING
        val encryptedId = encrypt(stockId.toString())
        val detailApi= ApiClient.client?.create(DetailApiInterface::class.java)
        detailApi?.getDetail(DetailRequestModel(encryptedId), handshakeMap[AUTH])
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<DetailModel>(){
                override fun onSuccess(t: DetailModel) {
                    detailResponseStatus.value = DetailResponseStatus.READY
                    detail.value = t
                }
                override fun onError(e: Throwable) {
                    detailResponseStatus.value = DetailResponseStatus.FAILURE
                    Log.e("DetailRequestError" , e.toString())
                }
            })?.let {
                disposable.add(it)
            }
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
