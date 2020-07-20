package com.bryanalvarez.validgeoservices.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArtistViewModelFactory: ViewModelProvider.Factory{

    var isNetworkConnected: Boolean
    var application: Application

    constructor(isNetworkConnected: Boolean, application: Application){
        this.isNetworkConnected = isNetworkConnected
        this.application = application
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return  ArtistsViewModel(isNetworkConnected, application) as T
    }

}