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
import com.bryanalvarez.validgeoservices.model.Artist
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_artist_detail.*
import java.text.NumberFormat


/**
 * A simple [Fragment] subclass.
 */
class ArtistDetailFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarConference.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.ic_close_black_24dp)
        toolbarConference.setTitleTextColor(Color.WHITE)
        toolbarConference.setNavigationOnClickListener{
            dismiss()
        }

        val format = NumberFormat.getInstance();
        val artist = arguments?.getSerializable("artist") as Artist

        toolbarConference.title =  artist.artistName
        Glide.with(ivDetailArtistPhoto.context)
            .load(artist?.artistImage?.get(3)?.text)
            .into(ivDetailArtistPhoto)
        tvDetailArtistName.text = artist.artistName
        tvDetailArtistListeners.text =  format.format(artist.artistListeners.toInt())
        tvDetailArtistMbid.text =  artist.artistMbid

        llDetailArtistListenNow.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(artist.artistUrl)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }


}
