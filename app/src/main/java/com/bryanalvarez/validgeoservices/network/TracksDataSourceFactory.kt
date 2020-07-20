package com.bryanalvarez.validgeoservices.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bryanalvarez.validgeoservices.model.Track
import com.bryanalvarez.validgeoservices.network.api.NetworkService

class TracksDataSourceFactory (private val networkService: NetworkService,
                               private var isNetworkConnected: Boolean,
                               var application: Application,
                               var search: String): DataSource.Factory<Int, Track>(){

    val tracksDataSourceLiveData = MutableLiveData<TracksDataSource>()

    override fun create(): DataSource<Int, Track> {
        val tracksDataSource = TracksDataSource(networkService,isNetworkConnected,application,search)
        tracksDataSourceLiveData.postValue(tracksDataSource)
        return tracksDataSource
    }

    fun setNewSearch(search: String){
        this.search = search
    }

    fun getTracks(): MutableLiveData<TracksDataSource>{
        return tracksDataSourceLiveData
    }

}