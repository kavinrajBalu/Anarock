package com.anarock.cpsourcing.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.model.EventCreationPayload
import com.anarock.cpsourcing.model.EventCreationResponse
import com.anarock.cpsourcing.retrofit.ApiClient
import com.anarock.cpsourcing.retrofit.EventAPIService
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateEventRepository {
    companion object{
        private val eventAPIService: EventAPIService = ApiClient.getEventApiService()!!
        fun createEvent(payload: EventCreationPayload,context: Context): MutableLiveData<Boolean>
        {
            var result : MutableLiveData<Boolean> = MutableLiveData()
            val bodyJson: String = Gson().toJson(payload)

            val requestBody: RequestBody = RequestBody.create(
                okhttp3.MediaType.parse(
                    context.getString(
                        R.string.request_type
                    )
                ), bodyJson
            )
            val call = eventAPIService.createEvent(requestBody)

            call.enqueue(object : Callback<EventCreationResponse>
            {
                override fun onFailure(call: Call<EventCreationResponse>, t: Throwable) {
                      Log.d("failed",t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<EventCreationResponse>,
                    response: Response<EventCreationResponse>
                ) {
                    if(response.isSuccessful)
                    {
//                        TODO : Add response value to local DB and return response status to UI.
                        val eventCreationResponse = response.body()
                        result.value = true
                    }
                }
            })
          return result
        }
    }

}