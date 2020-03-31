package com.anarock.cpsourcing.retrofit

import com.anarock.cpsourcing.BuildConfig
import okhttp3.*

class MockInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val responseString = when {
                uri.contains("create") -> CreateEventResponse
                else -> ""
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create(
                        MediaType.parse("application/json"),
                        responseString.toByteArray()
                    )
                )
                .addHeader("content-type", "application/json")
                .build()
        }
        else
        {
            throw IllegalAccessError(
                "Anarock MockInterceptor is only meant for dev purpose and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

}
const val CreateEventResponse = "{\n" +
        "    \"id\": 13,\n" +
        "    \"event_type_id\": 3,\n" +
        "    \"cp_id\": 4,\n" +
        "    \"lead_id\": 3,\n" +
        "    \"start_time\": \"2020-03-26T07:30:00.000Z\",\n" +
        "    \"reminder_time\": \"2020-03-26T09:00:00.000Z\",\n" +
        "    \"status\": \"start\",\n" +
        "    \"meta_data\": null,\n" +
        "    \"created_at\": \"2020-03-26T05:36:40.278Z\",\n" +
        "    \"updated_at\": \"2020-03-26T05:36:40.278Z\",\n" +
        "    \"project_id\": 1\n" +
        "}"