package com.bryanalvarez.validgeoservices.view.ui.fragments


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.bryanalvarez.validgeoservices.R
import com.bryanalvarez.validgeoservices.model.Track
import com.bryanalvarez.validgeoservices.view.adapters.TrackListAdapter
import com.bryanalvarez.validgeoservices.view.adapters.TrackListener
import com.bryanalvarez.validgeoservices.viewmodel.TracksViewModel
import com.bryanalvarez.validgeoservices.viewmodel.TracksViewModelFactory
import kotlinx.android.synthetic.main.fragment_tracks.*

/**
 * A simple [Fragment] subclass.
 */
class TracksFragment : Fragment(), TrackListener  {

    private lateinit var viewModel: TracksViewModel
    private lateinit var trackListAdapter: TrackListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tracks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return

        viewModel =  ViewModelProviders.of(activity,TracksViewModelFactory(getNetworkState(), activity.application)).get(TracksViewModel::class.java)

        trackListAdapter = TrackListAdapter(this)
        rvTracks.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = trackListAdapter
            addItemDecoration(DividerItemDecoration(rvTracks.getContext(), DividerItemDecoration.VERTICAL))
        }

        etSearchTrack.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setNewSearch(s.toString())
            }

        })

        observeViewModel()

    }

    private fun observeViewModel() {
        viewModel.tracksList.observe(this, Observer {
            rlBaseTracks.visibility = View.INVISIBLE
            trackListAdapter.submitList(it)
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                rlBaseTracks.visibility = View.VISIBLE
            }else{
                rlBaseTracks.visibility = View.INVISIBLE
            }
        })
    }

    private fun getNetworkState(): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return (networkInfo != null && networkInfo.isConnected)
    }

    override fun onTrackClicked(track: Track?, position: Int) {
        var bundle = bundleOf("track" to track)
        findNavController().navigate(R.id.tracktDetailFragmentDialog, bundle)
    }


}
