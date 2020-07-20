package com.bryanalvarez.validgeoservices.network.repo

import android.app.Application
import com.bryanalvarez.validgeoservices.model.Track
import com.bryanalvarez.validgeoservices.network.db.dao.TrackDao
import com.bryanalvarez.validgeoservices.network.db.GEODatabase

class TrackRepository(application: Application){

    val trackDao: TrackDao

    init {
        var db = GEODatabase.getDatabase(application)
        trackDao = db.trackDao()
    }

    fun getTrackData(): List<Track>{
        return trackDao.getTracks()
    }

    fun addTrack(track: Track){
        trackDao.insert(track)
    }

    fun getTracksByName(search: String): List<Track>{
        return trackDao.getTracksByName(search)
    }

    fun clearTracks(){
        trackDao.clearTracks()
    }
}