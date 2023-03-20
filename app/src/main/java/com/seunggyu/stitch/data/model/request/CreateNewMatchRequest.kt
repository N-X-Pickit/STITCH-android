package com.seunggyu.stitch.data.model.request


import com.squareup.moshi.Json

data class CreateNewMatchRequest(
    @Json(name = "detail")//
    val detail: String?,
    @Json(name = "duration")//
    val duration: Int?,
    @Json(name = "eventType")//
    val eventType: String?,
    @Json(name = "fee")//
    val fee: Int?,
    @Json(name = "hostId")//
    val hostId: String?,
    @Json(name = "id")//
    val id: String?,
    @Json(name = "imageUrl")//
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
    @Json(name = "numOfMembsers")
    val numOfMembsers: Int?,
    @Json(name = "startTime")
    val startTime: String?,
    @Json(name = "isTeach")
    val isTeach: Boolean?,
)