package com.anarock.cpsourcing.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        //TODO : Change end points to anarock.
        private val BASE_URL = "https://meta.anarock.com/api/v0/"
        private val API_VERSION = "/v1/"
        private  var retrofit: Retrofit? = null
        private fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL + API_VERSION)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    /*.client(OkHttpClient().newBuilder().addInterceptor(MockInterceptor()).build())*/
                    .client(OkHttpClient().newBuilder().build())
                    .build()
            }
            return retrofit
        }

        fun getService() : ApiService?
        {
            return getClient()?.create(ApiService::class.java)
        }
    }
}