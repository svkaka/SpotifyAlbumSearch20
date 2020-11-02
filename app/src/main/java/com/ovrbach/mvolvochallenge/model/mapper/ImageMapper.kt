package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.search.AlbumsSearchResponse
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem

class ImageMapper {
    fun toDomain(item: AlbumsSearchResponse.Albums.AlbumShort.Image) =
        AlbumItem.Image(
            height = item.height,
            url = item.url
        )
}
