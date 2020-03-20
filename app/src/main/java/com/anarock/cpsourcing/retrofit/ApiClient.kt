package com.anarock.cpsourcing.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        //TODO : Change end points to anarock.
        var DOMAIN_ANAROCK ="anarock.com"
        private val BASE_URL = "https://"
        private val API_VERSION = "/api/v0/"
        var META_DOMAIN = "meta"
        var EMPLOYEE_DOMAIN = "employee"
        var DOMAIN_NAME = "$META_DOMAIN.$DOMAIN_ANAROCK"
        private  var retrofit: Retrofit? = null
        private fun getClient(): Retrofit? {
            if (retrofit == null) {

                val interceptor = HttpLoggingInterceptor()

                interceptor.level = (HttpLoggingInterceptor.Level.BODY)

                val client = OkHttpClient().newBuilder().addInterceptor(interceptor)
                    .writeTimeout(1, TimeUnit.MINUTES).build()


                retrofit = Retrofit.Builder().baseUrl(BASE_URL+ DOMAIN_NAME + API_VERSION)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                    /*.client(OkHttpClient().newBuilder().addInterceptor(MockInterceptor()).build())*/
                    .client(client)
                    .build()
            }
            return retrofit
        }

        fun getService() : ApiService?
        {
            return getClient()?.create(ApiService::class.java)
        }

        fun setDomainName(domainType:String, domainName : String){
            DOMAIN_NAME = "$domainType.$domainName"
            println("domainName:$DOMAIN_NAME")
        }

        fun resetRetrofit(){
            retrofit = null
        }
    }
}