package com.anarock.cpsourcing.objects

import com.google.gson.annotations.SerializedName

internal class APIError
{
     @SerializedName("code")
     var statusCode : String? = null
     @SerializedName("message")
     var message : String? = null
}