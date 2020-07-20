package com.bryanalvarez.validgeoservices.network.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.bryanalvarez.validgeoservices.model.Artist

@Dao
interface ArtistDao {
    @Insert
    fun insert(artist: Artist)

    @Transaction
    @Query("SELECT * FROM artist")
    fun getArtists(): List<Artist>

    @Transaction
    @Query("SELECT * FROM artist WHERE artistName like :search")
    fun getArtistsByName(search: String): List<Artist>

    @Transaction
    @Query("DELETE FROM artist")
    fun clearArtists()

}