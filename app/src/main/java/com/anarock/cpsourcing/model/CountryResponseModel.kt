package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CountryResponseModel{
    @SerializedName("countries")
    @Expose
    var countries = ArrayList<CountryCodeModel>()
}