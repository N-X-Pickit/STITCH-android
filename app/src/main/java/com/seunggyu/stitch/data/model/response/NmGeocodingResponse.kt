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

data class Region(
    @Json(name = "area0")
    val area0: Area0?,
    @Json(name = "area1")
    val area1: Area1?,
    @Json(name = "area2")
    val area2: Area0?,
    @Json(name = "area3")
    val area3: Area0?,
    @Json(name = "area4")
    val area4: Area0?
)

data class Result(
    @Json(name = "code")
    val code: Code?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "region")
    val region: Region?
)

data class ReverseGeocodingResponse(
    @Json(name = "results")
    val results: List<Result>?,
    @Json(name = "status")
    val status: Status?
)

data class Status(
    @Json(name = "code")
    val code: Int?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "name")
    val name: String?
)

data class Coords(
    @Json(name = "center")
    val center: Center?
)

data class Code(
    @Json(name = "id")
    val id: String?,
    @Json(name = "mappingId")
    val mappingId: String?,
    @Json(name = "type")
    val type: String?
)

data class Area1(
    @Json(name = "alias")
    val alias: String?,
    @Json(name = "coords")
    val coords: Coords?,
    @Json(name = "name")
    val name: String?
)

data class Area0(
    @Json(name = "coords")
    val coords: Coords?,
    @Json(name = "name")
    val name: String?
)

data class Center(
    @Json(name = "crs")
    val crs: String?,
    @Json(name = "x")
    val x: Double?,
    @Json(name = "y")
    val y: Double?
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
