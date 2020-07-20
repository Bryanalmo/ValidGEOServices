package com.bryanalvarez.validgeoservices.view.adapters

import com.bryanalvarez.validgeoservices.model.Track

interface TrackListener {
    fun onTrackClicked(track: Track?, position: Int )
}