package com.bryanalvarez.validgeoservices.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bryanalvarez.validgeoservices.model.Artist
import com.bryanalvarez.validgeoservices.network.ArtistsDataSource
import com.bryanalvarez.validgeoservices.network.ArtistsDataSourceFactory
import com.bryanalvarez.validgeoservices.network.api.NetworkService

class ArtistsViewModel(var isNetworkConnected: Boolean, var application: Application): ViewModel(){

    private val networkService = NetworkService.getService()
    var artistsList: LiveData<PagedList<Artist>>
    var loading: LiveData<Boolean>
    private val pageSize = 10
    private val artistsDataSourceFactory: ArtistsDataSourceFactory
    var search: String = ""

    init{
        artistsDataSourceFactory = ArtistsDataSourceFactory(networkService, isNetworkConnected, application, search)
        loading = Transformations.switchMap(artistsDataSourceFactory.getArtists(), ArtistsDataSource::loading)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        artistsList = LivePagedListBuilder<Int, Artist>(artistsDataSourceFactory, config).build()
    }

     fun setNewSearch(search: String){
         this.search = search
         artistsDataSourceFactory.setNewSearch(search)
         artistsDataSourceFactory.getArtists().value?.invalidate()
     }
}