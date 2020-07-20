package com.bryanalvarez.validgeoservices.network

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bryanalvarez.validgeoservices.model.Track
import com.bryanalvarez.validgeoservices.network.api.NetworkService
import com.bryanalvarez.validgeoservices.network.repo.TrackRepository

class TracksDataSource(private val networkService: NetworkService,
                       private val isNetworkConnected: Boolean,
                       var application: Application,
                       var search: String): PageKeyedDataSource<Int, Track>(){

    var trackRepository: TrackRepository
    var loading: MutableLiveData<Boolean>

    init{
        trackRepository = TrackRepository(application)
        loading = MutableLiveData()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Track>) {
        loading.postValue(true)
        if(search.length>0){
            var tracksDataFilter = trackRepository.getTracksByName("%${search}%")
            loading.postValue(false)
            if(tracksDataFilter.isNotEmpty()){
                callback.onResult(tracksDataFilter,null,2)
                return
            }
        }else  if(isNetworkConnected){
            trackRepository.clearTracks()
            networkService.getTracks("geo.gettoptracks",
            "spain","829751643419a7128b7ada50de590067","json",1,10)
            .subscribe({
                    response ->
                        loading.postValue(false)
                        for (trackData in response.tracks.track){
                            trackRepository.addTrack(trackData)
                        }
                        callback.onResult(response.tracks.track, null, 2)
            }, { t -> Log.w("Error", "Error Load") })
        }else{
            var tracksData = trackRepository.getTrackData()
            callback.onResult(tracksData,null,2)
            loading.postValue(false)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Track>) {
        if(search.length == 0 && isNetworkConnected) {
            loading.postValue(true)
            networkService.getTracks("geo.gettoptracks",
                "spain", "829751643419a7128b7ada50de590067", "json", params.key, 10)
                .subscribe({ response ->
                    loading.postValue(false)
                    for (track in response.tracks.track) {
                        trackRepository.addTrack(track)
                    }
                    callback.onResult(response.tracks.track, params.key + 1)
                }, { t -> Log.w("Error", "Error Load") })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Track>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}