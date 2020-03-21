package com.anarock.cpsourcing.utilities

import com.anarock.cpsourcing.model.APIError
import com.anarock.cpsourcing.retrofit.ApiClient
import retrofit2.Response


internal  object ErrorUtils {
    fun parseError(response: Response<Any>): APIError? {
        return ApiClient.getClient()?.responseBodyConverter<APIError>(
            APIError::class.java,
            arrayOf()
        )?.convert(response.errorBody())
    }
}
