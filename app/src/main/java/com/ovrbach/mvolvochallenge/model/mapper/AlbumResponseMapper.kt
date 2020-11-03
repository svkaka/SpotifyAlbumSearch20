package com.ovrbach.mvolvochallenge.model.mapper

import android.net.Uri
import com.ovrbach.mvolvochallenge.model.dto.response.AlbumsSearchResponse
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.entity.AlbumSearchResponse
import javax.inject.Inject

class AlbumResponseMapper @Inject constructor(
        private val artistMapper: ArtistMapper,
        private val imageMapper: ImageMapper
) {

    fun toDomain(albumsSearchResponse: AlbumsSearchResponse): AlbumSearchResponse = AlbumSearchResponse(
            albums = albumsSearchResponse.albums.items.map { album ->
                AlbumItem(
                        id = album.id,
                        name = album.name,
                        releaseDate = album.release_date,
                        type = album.type,
                        uri = album.uri,
                        artists = album.artists.map { artist -> artistMapper.toDomain(artist) },
                        images = album.images.map { image -> imageMapper.toDomain(image) }
                )
            },
            offset = albumsSearchResponse.albums.offset,
            next = parseNextOffset(albumsSearchResponse.albums.next)
    )

    private fun parseNextOffset(next: String) = try {
        Uri.parse(next).let { uri -> uri.getQueryParameter("offset")?.toInt() }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        null
    }

}

