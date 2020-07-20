package com.bryanalvarez.validgeoservices.network.repo

import android.app.Application
import com.bryanalvarez.validgeoservices.model.Artist
import com.bryanalvarez.validgeoservices.network.db.dao.ArtistDao
import com.bryanalvarez.validgeoservices.network.db.GEODatabase

class ArtistsRepository(application: Application) {

    val artistDao: ArtistDao

    init {
        var db = GEODatabase.getDatabase(application)
        artistDao = db.artistDao()
    }

    fun getArtistsData(): List<Artist>{
        return artistDao.getArtists()
    }

    fun addArtist(artist: Artist){
        artistDao.insert(artist)
    }

    fun getArtistsByName(search: String): List<Artist>{
        return artistDao.getArtistsByName(search)
    }

    fun clearArtists(){
        artistDao.clearArtists()
    }
}