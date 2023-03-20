package com.seunggyu.stitch.data.model.request

import com.squareup.moshi.Json

data class SignupRequest(
    @Json(name = "id")
    val id: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "introduce")
    val introduce: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "sports")
    val sports: List<String?>?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "type")
    val type: String?
)