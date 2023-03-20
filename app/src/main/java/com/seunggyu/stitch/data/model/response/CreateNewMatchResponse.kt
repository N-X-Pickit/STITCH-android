package com.seunggyu.stitch.data.model.response


import com.squareup.moshi.Json

data class CreateNewMatchResponse(
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
    val type: String?,
    @Json(name = "x")
    val x: String?,
    @Json(name = "y")
    val y: String?
)