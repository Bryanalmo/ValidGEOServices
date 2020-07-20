package com.bryanalvarez.validgeoservices.view.ui.fragments


import android.content.Context
import android.os.Bundle
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
import androidx.recyclerview.widget.RecyclerView

import com.bryanalvarez.validgeoservices.R
import com.bryanalvarez.validgeoservices.model.Artist
import com.bryanalvarez.validgeoservices.view.adapters.ArtistListAdapter
import com.bryanalvarez.validgeoservices.view.adapters.ArtistListener
import com.bryanalvarez.validgeoservices.viewmodel.ArtistsViewModel
import kotlinx.android.synthetic.main.fragment_artists.*
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import com.bryanalvarez.validgeoservices.viewmodel.ArtistViewModelFactory


/**
 * A simple [Fragment] subclass.
 */
class ArtistsFragment : Fragment(), ArtistListener {


    private lateinit var viewModel: ArtistsViewModel
    private lateinit var artistListAdapter: ArtistListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity ?: return

        viewModel =  ViewModelProviders.of(activity, ArtistViewModelFactory(getNetworkState(), activity.application)).get(ArtistsViewModel::class.java)

        artistListAdapter = ArtistListAdapter(this)
        rvArtists.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = artistListAdapter
            addItemDecoration(DividerItemDecoration(rvArtists.getContext(), DividerItemDecoration.VERTICAL))
        }

        etSearchArtist.addTextChangedListener(object: TextWatcher{
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
        viewModel.artistsList.observe(this, Observer {
            rlBaseArtists.visibility = View.INVISIBLE
            artistListAdapter.submitList(it)
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                rlBaseArtists.visibility = View.VISIBLE
            }else{
                rlBaseArtists.visibility = View.INVISIBLE
            }
        })
    }

    private fun getNetworkState(): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return (networkInfo != null && networkInfo.isConnected)
    }

    override fun onArtistClicked(artist: Artist?, position: Int) {
        var bundle = bundleOf("artist" to artist)
        findNavController().navigate(R.id.artistDetailFragmentDialog, bundle)
    }

}
