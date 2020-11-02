package com.ovrbach.mvolvochallenge.model.mapper

import com.ovrbach.mvolvochallenge.model.dto.ImageDTO
import com.ovrbach.mvolvochallenge.model.entity.AlbumItem
import com.ovrbach.mvolvochallenge.model.entity.Image
import javax.inject.Inject

class ImageMapper @Inject constructor() {
    fun toDomain(item: ImageDTO) =
        Image(
            height = item.height,
            url = item.url
        )
}
