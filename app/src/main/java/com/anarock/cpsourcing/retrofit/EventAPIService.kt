package com.anarock.cpsourcing.retrofit

import com.anarock.cpsourcing.model.CPSearchResponse
import com.anarock.cpsourcing.model.EventCreationResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface EventAPIService
{

    @POST("create/")
    fun createEvent(@Body requestBody: RequestBody): Call<EventCreationResponse>

    @GET("cp-firm/predict")
    fun searchCP(@Query("q") hint : String , @Header("auth_token")token : String): Call<CPSearchResponse>

}