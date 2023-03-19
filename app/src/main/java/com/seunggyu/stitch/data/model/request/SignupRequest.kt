package com.seunggyu.stitch.data.model.request

data class SignupRequest(
    val id: String,
    val type: String,
    val location: String,
    val imageUrl: String,
    val name: String,
    val sports: List<String>,
    val introduce: String,
    )