package com.seunggyu.stitch.data

import com.seunggyu.stitch.data.model.request.AddressSearchRequest
import com.seunggyu.stitch.data.model.response.NetworkResponse
import com.seunggyu.stitch.data.model.request.SignupRequest
import com.seunggyu.stitch.data.model.response.AddressSearchServerResponse
import com.seunggyu.stitch.data.model.response.NmGeocodingResponse
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    //    @FormUrlEncoded
    @POST("member")
    suspend fun signup(
        @Body signupRequest: SignupRequest,
    ): Response<NetworkResponse>


    // 지오코딩 : 동이름 검색 -> 주소 반환
    @GET("map-geocode/v2/geocode")
    suspend fun geocoding(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("query") query: String
    ): Response<NmGeocodingResponse>

    // 리버스 지오코딩 : 좌표 -> 상세주소 반환
    @GET("map-reversegeocode/v2/gc")
    suspend fun reverseGeocoding(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("query") query: String
    ): Response<NmGeocodingResponse>

    // 근처동네 조회
    @GET("view/getNearAddress/address={address}")
    suspend fun getNearAddress(
        @Path("address") address: String
    ): Response<AddressSearchServerResponse>
}