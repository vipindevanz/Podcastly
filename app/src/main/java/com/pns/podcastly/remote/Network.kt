package com.pns.podcastly.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    companion object {
        fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("https://listen-api.listennotes.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}