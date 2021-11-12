package com.pns.podcastly.remote.model


import com.google.gson.annotations.SerializedName

data class ListenNoteResponseDTO(
    @SerializedName("has_next")
    val hasNext: Boolean,
    @SerializedName("has_previous")
    val hasPrevious: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("listennotes_url")
    val listennotesUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("next_page_number")
    val nextPageNumber: Int,
    @SerializedName("page_number")
    val pageNumber: Int,
    @SerializedName("parent_id")
    val parentId: Int,
    @SerializedName("podcasts")
    val podcasts: List<Podcast>,
    @SerializedName("previous_page_number")
    val previousPageNumber: Int,
    @SerializedName("total")
    val total: Int
)