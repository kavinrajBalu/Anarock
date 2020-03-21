package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TenantDomainModel {
    @SerializedName("domain")
    @Expose
    var domain: String = ""
}