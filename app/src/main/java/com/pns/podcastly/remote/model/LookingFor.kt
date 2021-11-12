package com.pns.podcastly.remote.model


import com.google.gson.annotations.SerializedName

data class LookingFor(
    @SerializedName("cohosts")
    val cohosts: Boolean,
    @SerializedName("cross_promotion")
    val crossPromotion: Boolean,
    @SerializedName("guests")
    val guests: Boolean,
    @SerializedName("sponsors")
    val sponsors: Boolean
)