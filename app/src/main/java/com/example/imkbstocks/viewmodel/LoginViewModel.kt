package com.example.imkbstocks.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.imkbstocks.*
import com.example.imkbstocks.deviceId
import com.example.imkbstocks.deviceModel
import com.example.imkbstocks.manufacturer
import com.example.imkbstocks.model.HandshakeModel
import com.example.imkbstocks.model.HandshakeRequestModel
import com.example.imkbstocks.service.ApiClient
import com.example.imkbstocks.service.HandshakeApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application) : AndroidViewModel(application){
    private val disposable = CompositeDisposable()
    val handshakeStatus = MutableLiveData<HandshakeStatus>()

    internal fun handshake(){
        handshakeStatus.value = HandshakeStatus.IN_PROGRESS
        val requestModel = HandshakeRequestModel(deviceId,deviceModel,manufacturer,PLATFORM_NAME,systemVersion)
        val handshakesApi= ApiClient.client?.create(HandshakeApiInterface::class.java)
        handshakesApi?.getHandshake(requestModel)
            ?.subscribeOn(Schedulers.newThread())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeWith(object : DisposableSingleObserver<HandshakeModel>(){
            override fun onSuccess(t: HandshakeModel) {
                handshakeMap.clear()
                handshakeMap[AES_KEY] = t.aesKey
                handshakeMap[AES_IV] = t.aesIV
                handshakeMap[AUTH] = t.authorization
                handshakeStatus.value = HandshakeStatus.DONE
            }
            override fun onError(e: Throwable) {
                handshakeStatus.value = HandshakeStatus.FAILURE
                Log.e("Handshake Error" , e.toString())
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