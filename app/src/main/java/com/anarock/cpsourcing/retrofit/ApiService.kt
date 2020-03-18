package com.anarock.cpsourcing.retrofit

import com.anarock.cpsourcing.model.LoginResponseModel
import com.anarock.cpsourcing.model.TenantDomainResponseModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService
{

    @GET("tenant-config/")
    fun getTenantDomain(@Field("tenant_name") tenant_name:String):Call<TenantDomainResponseModel>

    @POST("api-token-auth/")
    @FormUrlEncoded
    fun loginUser(@Field("username") username:String,
                  @Field("password") password:String): Call<LoginResponseModel>



    /* @GET("categories/")
    fun getCategory(): Call<ArrayList<Category>>

    @POST("_search/")
    fun getSearchResult(@Body requestBody: RequestBody):Call<SearchResult>

    @POST("generate-otp/")
    fun generateOtp(@Field("type") type:String,
                    @Field("username") username:String):Call<SearchResult>

    @POST("verify-otp/")
    fun verifyOtp(@Field("otp") otp:Int,
                  @Field("username") username:String):Call<SearchResult>

    @POST("signup/")
    fun signup(@Field("username") username:String,
               @Field("first_name") first_name:String,
               @Field("password") password:String,
               @Field("isServiceProvider") isServiceProvider:Boolean,
               @Field("verifiedToken") verifiedToken:String):Call<SearchResult>



    @POST("change-password/")
    fun reserPassword(@Field("verificationToken") verificationToken:String,
                      @Field("username") username:String,
                      @Field("password") password:String):Call<SearchResult>*/

}