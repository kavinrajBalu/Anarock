package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CPSearchPayload(@SerializedName("q")
                             @Expose
                             var q: String? = null)