package com.bryanalvarez.validgeoservices.network.db.dao

import androidx.room.*
import com.bryanalvarez.validgeoservices.model.Track

@Dao
interface TrackDao {

    @Insert()
    fun insert(track: Track)

    @Transaction
    @Query("SELECT * FROM track")
    fun getTracks(): List<Track>

    @Transaction
    @Query("SELECT * FROM track WHERE name like :search")
    fun getTracksByName(search: String): List<Track>

    @Transaction
    @Query("DELETE FROM track")
    fun clearTracks()
}