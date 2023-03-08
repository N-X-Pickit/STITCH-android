package com.seunggyu.stitch.data.model.response

import com.squareup.moshi.Json

data class EventResponse(
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "title")
    val title: String?,
)
