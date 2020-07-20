package com.bryanalvarez.validgeoservices.view.adapters

import com.bryanalvarez.validgeoservices.model.Artist

interface ArtistListener {
    fun onArtistClicked(artist: Artist?, position: Int )
}