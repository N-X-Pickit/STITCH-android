package com.seunggyu.stitch.data.model.request


import com.squareup.moshi.Json

data class ReportRequest(
    @Json(name = "matchId")
    val matchId: String?,
    @Json(name = "memberId")
    val memberId: String?,
    @Json(name = "reason")
    val reason: String?,
    @Json(name = "reporterId")
    val reporterId: String?
)