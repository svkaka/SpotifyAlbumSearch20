package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.ArtistDTO
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.entity.Artist
import javax.inject.Inject

class ArtistMapper  @Inject constructor() {
    fun toDomain(artist: ArtistDTO) =
        Artist(
            name = artist.name,
            uri = artist.uri
        )
}