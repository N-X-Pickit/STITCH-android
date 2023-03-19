package com.seunggyu.stitch.data.model.response

import com.squareup.moshi.Json

data class NetworkResponse(
    @field:Json(name = "id")
    val id: String?,
    @field:Json(name = "type")
    val type: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "imageUrl")
    val imageUrl: String?,
    @field:Json(name = "location")
    val location: String?,
    @field:Json(name = "indroiduce")
    val hostId: String?,
    @field:Json(name = "fee")
    val fee: Int?,
    @field:Json(name = "contents")
    val contents: String?,
    @field:Json(name = "startTime")
    val startTime: String?,
    @field:Json(name = "duration")
    val duration: Int?,
    @field:Json(name = "sports")
    val sports: List<String>?,
)

data class RESULT(
    @field:Json(name = "result")
    val result: Boolean?,
)
