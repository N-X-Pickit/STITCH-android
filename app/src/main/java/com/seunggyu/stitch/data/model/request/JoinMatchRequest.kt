package com.seunggyu.stitch.data.model.request


import com.squareup.moshi.Json

data class JoinMatchRequest(
    @Json(name = "id")
    val id: String?
)