package com.seunggyu.stitch.data.model.response


import com.squareup.moshi.Json

data class ReportResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "matchId")
    val matchId: String?,
    @Json(name = "memberId")
    val memberId: String?,
    @Json(name = "reason")
    val reason: String?,
    @Json(name = "reporterId")
    val reporterId: String?,
    @Json(name = "sk")
    val sk: String?,
    @Json(name = "type")
    val type: String?
)