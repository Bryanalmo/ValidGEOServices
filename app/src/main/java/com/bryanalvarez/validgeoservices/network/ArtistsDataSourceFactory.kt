package com.bryanalvarez.validgeoservices.network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bryanalvarez.validgeoservices.model.Artist
import com.bryanalvarez.validgeoservices.network.api.NetworkService

class ArtistsDataSourceFactory (private val networkService: NetworkService,
                                private var isNetworkConnected: Boolean,
                                var application: Application,
                                var search: String): DataSource.Factory<Int, Artist>(){

    val artistsDataSourceLiveData = MutableLiveData<ArtistsDataSource>()


    override fun create(): DataSource<Int, Artist> {
        val artistsDataSource = ArtistsDataSource(networkService, isNetworkConnected, application, search)
        artistsDataSourceLiveData.postValue(artistsDataSource)
        return artistsDataSource
    }

    fun setNewSearch(search: String){
        this.search = search
    }

    fun getArtists(): MutableLiveData<ArtistsDataSource>{
        return artistsDataSourceLiveData
    }

}