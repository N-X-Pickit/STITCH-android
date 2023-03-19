package com.seunggyu.stitch.data.model.response

import com.squareup.moshi.Json

data class NmGeocodingBuildingResponse(
    @Json(name = "results")
    val results: List<ResultBuilding>?,
    @Json(name = "status")
    val status: Status?
)

data class ResultBuilding(
    @Json(name = "code")
    val code: Code?,
    @Json(name = "land")
    val land: Land?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "region")
    val region: Region?
)

data class Land(
    @Json(name = "addition0")
    val addition0: Addition?,
    @Json(name = "addition1")
    val addition1: Addition?,
    @Json(name = "addition2")
    val addition2: Addition?,
    @Json(name = "addition3")
    val addition3: Addition?,
    @Json(name = "addition4")
    val addition4: Addition?,
    @Json(name = "coords")
    val coords: Coords?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "number1")
    val number1: String?,
    @Json(name = "number2")
    val number2: String?,
    @Json(name = "type")
    val type: String?
)

data class Addition(
    @Json(name = "type")
    val type: String?,
    @Json(name = "value")
    val value: String?
)