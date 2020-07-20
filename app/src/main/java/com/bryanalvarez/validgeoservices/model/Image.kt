package com.bryanalvarez.validgeoservices.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Image (
    @PrimaryKey(autoGenerate = true) val idImage: Int,
    @SerializedName("#text") val  text: String,
    val size: String
):Serializable