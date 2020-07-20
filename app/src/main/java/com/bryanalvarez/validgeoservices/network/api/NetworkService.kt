package com.bryanalvarez.validgeoservices.network.api

import com.bryanalvarez.validgeoservices.model.Response
import com.bryanalvarez.validgeoservices.model.ResponseApiTracks
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService{



    @GET("2.0/")
    fun getArtists(
        @Query("method") method: String,
        @Query("country") country: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String,
        @Query("page") page: Int,
        @Query("limit") pageSize: Int): Single<Response>

    @GET("2.0/")
    fun getTracks(
        @Query("method") method: String,
        @Query("country") country: String,
        @Query("api_key") api_key: String,
        @Query("format") format: String,
        @Query("page") page: Int,
        @Query("limit") pageSize: Int): Single<ResponseApiTracks>

    companion object {
        private val client = OkHttpClient.Builder().build()

        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://ws.audioscrobbler.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}