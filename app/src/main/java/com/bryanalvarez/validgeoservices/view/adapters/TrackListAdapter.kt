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
import com.bryanalvarez.validgeoservices.model.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TrackListAdapter(val trackListener: TrackListener): PagedListAdapter<Track, TrackListAdapter.ViewHolder>(TracktDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tracks_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), trackListener)
    }


    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val tvTrackName = itemView.findViewById<TextView>(R.id.tvTrackName)
        val tvTrackArtistName = itemView.findViewById<TextView>(R.id.tvTrackArtistName)
        val ivTrackPhoto = itemView.findViewById<ImageView>(R.id.ivTrackPhoto)

        fun bind(track: Track?, trackListener: TrackListener){

            tvTrackName.text = track?.name
            tvTrackArtistName.text = track?.artist?.trackArtistName
            Glide.with(ivTrackPhoto.context)
                .load(track?.image?.get(0)?.text)
                .apply(RequestOptions.circleCropTransform())
                .into(ivTrackPhoto)

            itemView.setOnClickListener{
                trackListener.onTrackClicked(track, adapterPosition)
            }
        }
    }

    companion object {
        val TracktDiffCallback = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
                return oldItem == newItem
            }
        }
    }
}