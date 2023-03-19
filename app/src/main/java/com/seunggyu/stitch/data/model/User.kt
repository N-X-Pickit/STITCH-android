package com.seunggyu.stitch.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("sports")
    val sports: List<String>,

    @SerializedName("token")
    val token: String,

    @SerializedName("introduce")
    val introduce: String,

) : Parcelable
