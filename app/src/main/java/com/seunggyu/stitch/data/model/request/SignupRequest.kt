package com.seunggyu.stitch.data.model.request

data class SignupRequest(
    val id: String,
    val location: String,
    val imageUrl: String,
    val name: String,
    val sports: List<String>,
    val intro: String,
    )