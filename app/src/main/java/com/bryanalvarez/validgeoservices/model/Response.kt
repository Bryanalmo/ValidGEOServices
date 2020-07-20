package com.bryanalvarez.validgeoservices.model

import com.google.gson.annotations.SerializedName


//Artists
data class Response (
    val topartists: ArtistResponse
)

data class ArtistResponse(
    var artist: List<Artist>,
    @SerializedName("@attr") val  aditional: Aditional
)

//Tracks
data class ResponseApiTracks(
    val tracks: TrackResponse
)

data class TrackResponse(
    var track: List<Track>,
    @SerializedName("@attr") val  aditional: Aditional
)



data class Aditional(
    val page: String,
    val perPage: String
)