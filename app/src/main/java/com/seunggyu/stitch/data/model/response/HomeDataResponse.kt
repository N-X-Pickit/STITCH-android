package com.seunggyu.stitch.data.model.response


import com.squareup.moshi.Json

data class HomeDataResponse(
    @Json(name = "newMatches")
    val newMatches: List<NewMatch?>?,
    @Json(name = "recommendedMatches")
    val recommendedMatches: List<RecommendedMatch?>?
)
data class HostMember(
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
    @Json(name = "sk")
    val sk: String?,
    @Json(name = "sports")
    val sports: List<String?>?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "type")
    val type: String?
)
data class JoinedMember(
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
    @Json(name = "sk")
    val sk: String?,
    @Json(name = "sports")
    val sports: List<String?>?,
    @Json(name = "token")
    val token: String?,
    @Json(name = "type")
    val type: String?
)

data class Match(
    @Json(name = "detail")
    val detail: String?,
    @Json(name = "duration")
    val duration: Int?,
    @Json(name = "eventType")
    val eventType: String?,
    @Json(name = "fee")
    val fee: Int?,
    @Json(name = "hostId")
    val hostId: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "latitude")
    val latitude: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "longitude")
    val longitude: String?,
    @Json(name = "maxCapacity")
    val maxCapacity: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "numOfMembers")
    val numOfMembers: Int?,
    @Json(name = "sk")
    val sk: String?,
    @Json(name = "startTime")
    val startTime: String?,
    @Json(name = "teach")
    val teach: Boolean?,
    @Json(name = "type")
    val type: String?
)
data class NewMatch(
    @Json(name = "detail")
    val detail: String?,
    @Json(name = "duration")
    val duration: Int?,
    @Json(name = "eventType")
    val eventType: String?,
    @Json(name = "fee")
    val fee: Int?,
    @Json(name = "hostId")
    val hostId: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "imageUrl")
    val imageUrl: String?,
    @Json(name = "latitude")
    val latitude: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "longitude")
    val longitude: String?,
    @Json(name = "maxCapacity")
    val maxCapacity: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "numOfMembers")
    val numOfMembers: Int?,
    @Json(name = "sk")
    val sk: String?,
    @Json(name = "startTime")
    val startTime: String?,
    @Json(name = "teach")
    val teach: Boolean?,
    @Json(name = "type")
    val type: String?
)

data class RecommendedMatch(
    @Json(name = "hostMember")
    val hostMember: HostMember?,
    @Json(name = "joinedMembers")
    val joinedMembers: List<JoinedMember?>?,
    @Json(name = "match")
    val match: Match?
)
