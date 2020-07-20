package com.bryanalvarez.validgeoservices.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "artist")
data class Artist(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @SerializedName("name") val artistName: String,
    @SerializedName("listeners")val artistListeners: String,
    @SerializedName("mbid") val artistMbid: String,
    @SerializedName("url") val artistUrl: String,
    @SerializedName("image") var artistImage: List<Image>
): Serializable

