package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryCodeModel {
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("url_name")
    @Expose
    var urlName: String? = null
    @SerializedName("country_code")
    @Expose
    var countryCode: String? = null
    @SerializedName("operational")
    @Expose
    var operational: String? = null


}