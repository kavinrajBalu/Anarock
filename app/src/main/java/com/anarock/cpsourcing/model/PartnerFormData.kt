package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class PartnerFormData(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("phone")
    @Expose
    var phone: String? = null,

    @SerializedName("designation")
    @Expose
    var designation: String? = null,

    var editable: Boolean = false
)
