package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.search.AlbumsSearchResponse
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem

class ArtistMapper(

) {
    fun toDomain(artist: AlbumsSearchResponse.Albums.AlbumShort.Artist) =
        AlbumItem.Artist(
            name = artist.name,
            uri = artist.uri
        )
}