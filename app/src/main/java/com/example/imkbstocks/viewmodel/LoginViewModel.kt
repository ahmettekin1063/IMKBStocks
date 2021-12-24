package com.example.imkbstocks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.imkbstocks.LoginStatus

class LoginViewModel(application: Application) : AndroidViewModel(application){
    val loginStatus = MutableLiveData<LoginStatus>()

    internal fun login(){

    }

}