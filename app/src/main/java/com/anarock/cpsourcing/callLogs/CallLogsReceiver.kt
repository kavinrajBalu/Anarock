package com.anarock.cpsourcing.callLogs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.anarock.cpsourcing.model.CallLogsData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


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
           // val callDetails: Array<HashMap<String?,Any?>>? = data["callsDetails"] as Array<HashMap<String?, Any?>>?
            val json = Gson().toJson(data["callsDetails"])
            val listType: Type = object : TypeToken<List<CallLogsData?>?>() {}.type
            val callLogsData:List<CallLogsData> = Gson().fromJson(json,listType)
            for(callData in callLogsData)
            {
                val phone = callData.phone
                val duration = callData.duration
                val startTime = callData.startTime
                val callTypeId = callData.callTypeId
            }
        }
    }
}