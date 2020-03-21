package com.anarock.cpsourcing.model

data class OtpRequestModel(
    var phone: String? = null,
    var country_id: Int? = null,
    var otp: String? = null
)