package com.example.imkbstocks.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.imkbstocks.*
import com.example.imkbstocks.AES_IV
import com.example.imkbstocks.AES_KEY
import com.example.imkbstocks.AUTH
import com.example.imkbstocks.handshakeMap
import com.example.imkbstocks.model.ListModel
import com.example.imkbstocks.model.ListRequestModel
import com.example.imkbstocks.service.ApiClient
import com.example.imkbstocks.service.StocksApiInterface
import com.example.imkbstocks.util.encrypt
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.ScheduledRunnable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class StocksViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    val stockList = MutableLiveData<List<ListModel.Stock?>>()
    val stockListStatus = MutableLiveData<StockListStatus>()

    internal fun getStockList(period: String){
        stockListStatus.value = StockListStatus.PREPARING
        val encryptedPeriod = encrypt(period)
        val stocksApi = ApiClient.client?.create(StocksApiInterface::class.java)
        stocksApi?.getStocks(ListRequestModel(encryptedPeriod), handshakeMap[AUTH])
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<ListModel>(){
                override fun onSuccess(t: ListModel) {
                    stockListStatus.value = StockListStatus.READY
                    stockList.value = t.stocks
                }

                override fun onError(e: Throwable) {
                    stockListStatus.value = StockListStatus.FAILURE
                    Log.e("StockListRequest Error" , e.toString())
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