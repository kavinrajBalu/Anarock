package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TenantDomainResponseModel {

    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("response")
    @Expose
    var response: TenantDomainModel? = null

}
