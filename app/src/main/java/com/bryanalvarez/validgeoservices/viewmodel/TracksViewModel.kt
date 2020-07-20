package com.bryanalvarez.validgeoservices.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bryanalvarez.validgeoservices.model.Track
import com.bryanalvarez.validgeoservices.network.api.NetworkService
import com.bryanalvarez.validgeoservices.network.TracksDataSource
import com.bryanalvarez.validgeoservices.network.TracksDataSourceFactory

class TracksViewModel(var isNetworkConnected: Boolean, var application: Application): ViewModel(){

    private val networkService = NetworkService.getService()
    var tracksList: LiveData<PagedList<Track>>
    var loading: LiveData<Boolean>
    private val pageSize = 10
    private val tracksDataSourceFactory: TracksDataSourceFactory
    var search: String = ""

    init{
        tracksDataSourceFactory = TracksDataSourceFactory(networkService,isNetworkConnected,application,search)
        loading = Transformations.switchMap(tracksDataSourceFactory.getTracks(), TracksDataSource::loading)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        tracksList = LivePagedListBuilder<Int, Track>(tracksDataSourceFactory, config).build()
    }

    fun setNewSearch(search: String){
        this.search = search
        tracksDataSourceFactory.setNewSearch(search)
        tracksDataSourceFactory.getTracks().value?.invalidate()
    }
}