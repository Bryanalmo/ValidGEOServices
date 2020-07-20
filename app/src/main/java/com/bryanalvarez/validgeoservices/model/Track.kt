package com.bryanalvarez.validgeoservices.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "track")
data class Track(
    @PrimaryKey(autoGenerate = true) val idTrack: Int,
    val name: String,
    val duration: String,
    val listeners: String,
    val mbid: String,
    val url: String,
    @Embedded val artist: TrackArtist,
    val image: List<Image>
): Serializable

@Entity
data class TrackArtist(
    @PrimaryKey(autoGenerate = true) val idTrackArtist: Int,
    @SerializedName("name") val trackArtistName: String,
    @SerializedName("mbid") val trackArtistMbid: String,
    @SerializedName("url") val trackArtistUrl: String
): Serializable