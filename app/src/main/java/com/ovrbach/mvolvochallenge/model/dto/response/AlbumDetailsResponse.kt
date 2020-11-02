package com.ovrbach.mvolvochallenge.model.dto.response

import com.ovrbach.mvolvochallenge.model.dto.ArtistDTO
import com.ovrbach.mvolvochallenge.model.dto.ImageDTO
import com.ovrbach.mvolvochallenge.model.dto.TracksDTO

data class AlbumDetailsResponse(
    val album_type: String,
    val artists: List<ArtistDTO>,
    val images: List<ImageDTO>,
    val label: String,
    val name: String,
    val release_date: String,
    val tracks: TracksDTO,
    val uri: String
)
