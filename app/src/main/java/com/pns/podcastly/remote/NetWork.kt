package com.pns.podcastly.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetWork {
    companion object {
        fun getRetrofit() = Retrofit.Builder()
            .baseUrl("https://listen-api.listennotes.com/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}