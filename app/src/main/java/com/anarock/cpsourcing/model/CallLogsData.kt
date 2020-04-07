package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CallLogsData(
    @SerializedName("duration")
    @Expose
    var duration : Int = 0,

    @SerializedName("startTime")
    @Expose
     var startTime : Double = 0.0,

    @SerializedName("callTypeId")
    @Expose
     var callTypeId : Int = 0,

    @SerializedName("phone")
    @Expose
     var phone : String
)