package com.bryanalvarez.validgeoservices.view.ui.fragments


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment

import com.bryanalvarez.validgeoservices.R
import com.bryanalvarez.validgeoservices.model.Track
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_track_detail.*
import java.text.NumberFormat
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 */
class TrackDetailFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarTrack.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.ic_close_black_24dp)
        toolbarTrack.setTitleTextColor(Color.WHITE)
        toolbarTrack.setNavigationOnClickListener{
            dismiss()
        }

        val format = NumberFormat.getInstance();
        val track = arguments?.getSerializable("track") as Track

        toolbarTrack.title =  track.name
        Glide.with(ivDetailTrackPhoto.context)
            .load(track?.image?.get(3)?.text)
            .into(ivDetailTrackPhoto)
        tvDetailTrackName.text = track.name
        tvDetailTrackListeners.text =  format.format(track.listeners.toInt())
        tvDetailTrackArtist.text =  track.artist.trackArtistName
        tvDetailTrackDuration.text =  TimeUnit.SECONDS.toMinutes(track.duration.toLong()).toString() + "m"

        llDetailTrackListenNow.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(track.url)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}
