package com.bryanalvarez.validgeoservices.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bryanalvarez.validgeoservices.R
import com.bryanalvarez.validgeoservices.model.Artist
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ArtistListAdapter(val artistListener: ArtistListener): PagedListAdapter<Artist, ArtistListAdapter.ViewHolder>(ArtistDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artists_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), artistListener)
    }


    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val tvArtistName = itemView.findViewById<TextView>(R.id.tvArtistName)
        val ivArtistPhoto = itemView.findViewById<ImageView>(R.id.ivArtistPhoto)

        fun bind(artist: Artist?, artistListener: ArtistListener){

            tvArtistName.text = artist?.artistName
            Glide.with(ivArtistPhoto.context)
                .load(artist?.artistImage?.get(0)?.text)
                .apply(RequestOptions.circleCropTransform())
                .into(ivArtistPhoto)

            itemView.setOnClickListener{
                artistListener.onArtistClicked(artist, adapterPosition)
            }
        }
    }

    companion object {
        val ArtistDiffCallback = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem.artistName == newItem.artistName
            }

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
                return oldItem == newItem
            }
        }
    }
}