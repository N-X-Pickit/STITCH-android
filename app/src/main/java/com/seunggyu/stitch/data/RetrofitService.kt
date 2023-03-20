package com.seunggyu.stitch.data

import com.seunggyu.stitch.data.model.User
import com.seunggyu.stitch.data.model.request.CreateNewMatchRequest
import com.seunggyu.stitch.data.model.request.SignupRequest
import com.seunggyu.stitch.data.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    // @FormUrlEncoded
    @POST("member")
    suspend fun signup(
        @Body signupRequest: SignupRequest,
    ): Response<SignupResponse>

    @PUT("member/update")
    suspend fun update(
        @Body signupRequest: SignupRequest,
    ): Response<SignupResponse>


    @POST("match/create")
    suspend fun createMatch(
        @Body createMatchRequest: CreateNewMatchRequest,
    ): Response<CreateNewMatchResponse>

    // 홈화면 매치 가져오기
    @GET("match/home")
    suspend fun getHomeData(): Response<HomeDataResponse>

    // 매치 상세 정보 가져오기
    @GET("match/info/id={id}")
    suspend fun getMatchDetail(
        @Path("id") id: String?,
    ): Response<MatchDetailResponse>


    // 지역검색 : 장소이름 -> 장소검색
    @GET("search/local.json")
    suspend fun searchLocation(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Query("query") query: String,
        @Query("display") display: String,
        @Query("start") start: String,
        @Query("sort") sort: String,
    ): Response<LocationSearchResponse>


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
        @Query("request") request: String,
        @Query("coords") coords: String,
        @Query("output") output: String,
        @Query("orders") orders: String,
    ): Response<ReverseGeocodingResponse>

    // 건물명 포함 리버스 지오코딩
    @GET("map-reversegeocode/v2/gc")
    suspend fun reverseBuildingGeocoding(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("request") request: String,
        @Query("coords") coords: String,
        @Query("output") output: String,
        @Query("orders") orders: String,
    ): Response<NmGeocodingBuildingResponse>

    // 근처동네 조회
    @GET("view/getNearAddress/address={address}")
    suspend fun getNearAddress(
        @Path("address") address: String
    ): Response<AddressSearchServerResponse>

    // 이미 가입된 회원인지 조회
    @GET("member/isMember/id={id}")
    suspend fun isMember(
        @Path("id") id: String
    ): Boolean

    // 유저 정보 조회
    @GET("member/info/id={id}")
    suspend fun getUserData(
        @Path("id") id: String
    ): Response<User>
}