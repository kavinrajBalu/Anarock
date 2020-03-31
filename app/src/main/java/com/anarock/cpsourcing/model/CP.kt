package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class CP(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("rera_id")
    @Expose
    var reraId: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null
)