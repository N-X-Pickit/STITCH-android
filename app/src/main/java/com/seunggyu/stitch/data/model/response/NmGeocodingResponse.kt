package com.seunggyu.stitch.data.model.response

import com.squareup.moshi.Json

data class NmGeocodingResponse(
    @Json(name = "addresses")
    val addresses: List<Addresses?>?,
    @Json(name = "errorMessage")
    val errorMessage: String?,
    @Json(name = "meta")
    val meta: Meta?,
    @Json(name = "status")
    val status: String?
)

data class Meta(
    @Json(name = "count")
    val count: Int?,
    @Json(name = "page")
    val page: Int?,
    @Json(name = "totalCount")
    val totalCount: Int?
)

data class AddressElement(
    @Json(name = "code")
    val code: String?,
    @Json(name = "longName")
    val longName: String?,
    @Json(name = "shortName")
    val shortName: String?,
    @Json(name = "types")
    val types: List<String?>?
)

data class Addresses(
    @Json(name = "addressElements")
    val addressElements: List<AddressElement?>?,
    @Json(name = "distance")
    val distance: Double?,
    @Json(name = "englishAddress")
    val englishAddress: String?,
    @Json(name = "jibunAddress")
    val jibunAddress: String?,
    @Json(name = "roadAddress")
    val roadAddress: String?,
    @Json(name = "x")
    val x: String?,
    @Json(name = "y")
    val y: String?
)
