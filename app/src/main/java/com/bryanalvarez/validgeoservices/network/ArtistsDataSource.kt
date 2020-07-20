package com.bryanalvarez.validgeoservices.network

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bryanalvarez.validgeoservices.model.Artist
import com.bryanalvarez.validgeoservices.network.api.NetworkService
import com.bryanalvarez.validgeoservices.network.repo.ArtistsRepository

class ArtistsDataSource(private val networkService: NetworkService,
                        private val isNetworkConnected: Boolean,
                        var application: Application,
                        var search: String) : PageKeyedDataSource<Int,Artist>(){

    var artistsRepository: ArtistsRepository
    var loading: MutableLiveData<Boolean>

    init{
        artistsRepository = ArtistsRepository(application)
        loading = MutableLiveData()
    }


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Artist>) {
        loading.postValue(true)
        if(search.length>0){
            var artistDataFilter = artistsRepository.getArtistsByName("%${search}%")
            loading.postValue(false)
            if(artistDataFilter.isNotEmpty()){
                callback.onResult(artistDataFilter,null,2)
                return
            }
        }else if(isNetworkConnected){
            artistsRepository.clearArtists()
            networkService.getArtists("geo.gettopartists",
                "spain","829751643419a7128b7ada50de590067","json",1,10)
                .subscribe({
                        response ->
                        loading.postValue(false)
                        for (artist in response.topartists.artist){
                            artistsRepository.addArtist(artist)
                        }
                        callback.onResult(response.topartists.artist, null, 2)
                }, { t -> Log.w("Error", "Error Load") })
        }else{
            var artistData = artistsRepository.getArtistsData()
            callback.onResult(artistData,null,2)
            loading.postValue(false)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        if(search.length == 0 && isNetworkConnected){
            loading.postValue(true)
            networkService.getArtists("geo.gettopartists",
                "spain","829751643419a7128b7ada50de590067","json", params.key,10)
                .subscribe({
                    response ->
                        loading.postValue(false)
                        for (artist in response.topartists.artist){
                            artistsRepository.addArtist(artist)
                        }
                        callback.onResult(response.topartists.artist, params.key +1)
                }, { t -> Log.w("Error", "Error Load") })
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Artist>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}