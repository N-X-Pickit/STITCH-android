package com.seunggyu.stitch.data

import com.seunggyu.stitch.data.model.response.NetworkResponse
import com.seunggyu.stitch.data.model.request.SignupRequest
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
//    @FormUrlEncoded
    @POST("member")
    suspend fun signup(
        @Body signupRequest: SignupRequest,
    ): Response<NetworkResponse>
}