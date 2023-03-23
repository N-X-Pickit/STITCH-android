package com.seunggyu.stitch.data.model.response


import com.squareup.moshi.Json

data class MatchDetailResponse(
    @Json(name = "joinedMembers")
    val joinedMembers: List<JoinedMember>?,
    @Json(name = "match")
    val match: Match?
)