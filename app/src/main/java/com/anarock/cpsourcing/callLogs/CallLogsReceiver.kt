package com.anarock.cpsourcing.callLogs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class CallLogsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val action = intent.action
        val data = intent.getSerializableExtra("data") as HashMap<String?, Any?>
        val eventName: String? = null
        if (action == "com.anarock.broadcast.CALL_EVENT") {
            val eventType = data["type"] as String
            val payload = data["payload"] as HashMap<String?,Any?>
            val phone = payload["phone"] as String
            if (eventType == "idle") {
                val duration = payload["duration"] as Int
                val startTime = payload["startTime"] as Double
                val callTypeId = payload["callTypeId"] as Int
            }
        } else if (action == "com.anarock.broadcast.CALLS_DETAILS_EVENT") {
            val callDetails: Array<HashMap<String?, Any?>>? = data["callDetails"] as Array<HashMap<String?, Any?>>?
            callDetails?.forEach { payload ->
                val phone = payload["phone"] as String?
                val duration = payload["duration"] as Long
                val startTime = payload["startTime"] as Long
                val callTypeId = payload["callTypeId"] as Int
            }
        }
    }
}