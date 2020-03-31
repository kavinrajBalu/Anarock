package com.anarock.cpsourcing.retrofit

import com.anarock.cpsourcing.model.EventCreationResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface EventAPIService
{

    @POST("create/")
    fun createEvent(@Body requestBody: RequestBody): Call<EventCreationResponse>

}