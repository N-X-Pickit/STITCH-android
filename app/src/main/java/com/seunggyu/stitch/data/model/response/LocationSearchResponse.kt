package com.seunggyu.stitch.data.model.response


import com.squareup.moshi.Json

data class LocationSearchResponse(
    @Json(name = "display")
    val display: Int?,
    @Json(name = "items")
    val items: List<Item?>?,
    @Json(name = "lastBuildDate")
    val lastBuildDate: String?,
    @Json(name = "start")
    val start: Int?,
    @Json(name = "total")
    val total: Int?
)

data class Item(
    @Json(name = "address")
    val address: String?,
    @Json(name = "category")
    val category: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "link")
    val link: String?,
    @Json(name = "mapx")
    val mapx: String?,
    @Json(name = "mapy")
    val mapy: String?,
    @Json(name = "roadAddress")
    val roadAddress: String?,
    @Json(name = "telephone")
    val telephone: String?,
    @Json(name = "title")
    val title: String?
)