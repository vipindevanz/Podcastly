package com.pns.podcastly.remote.model


import com.google.gson.annotations.SerializedName

data class Extra(
    @SerializedName("facebook_handle")
    val facebookHandle: String,
    @SerializedName("google_url")
    val googleUrl: String,
    @SerializedName("instagram_handle")
    val instagramHandle: String,
    @SerializedName("linkedin_url")
    val linkedinUrl: String,
    @SerializedName("patreon_handle")
    val patreonHandle: String,
    @SerializedName("spotify_url")
    val spotifyUrl: String,
    @SerializedName("twitter_handle")
    val twitterHandle: String,
    @SerializedName("url1")
    val url1: String,
    @SerializedName("url2")
    val url2: String,
    @SerializedName("url3")
    val url3: String,
    @SerializedName("wechat_handle")
    val wechatHandle: String,
    @SerializedName("youtube_url")
    val youtubeUrl: String
)