package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RemoteConfig {
    @SerializedName("latest_app_url")
    @Expose
    var latestAppUrl: String? = null
    @SerializedName("latest_app_version_code")
    @Expose
    var latestAppVersionCode: Int? = null
}