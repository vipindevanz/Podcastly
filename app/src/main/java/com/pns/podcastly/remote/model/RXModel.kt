package com.pns.podcastly.remote.model

sealed class RXModel {
    data class OnSuccess(val response: ListenNoteResponseDTO) : RXModel()
    data class OnFailure(val error: String) : RXModel()
}