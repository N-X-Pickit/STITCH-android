package com.seunggyu.stitch.data.model.response

import com.squareup.moshi.Json

data class AddressSearchServerResponse(
    @field:Json(name = "key")
    val key: String?,
    @field:Json(name = "value")
    val value: List<String>?,
)
