package com.anarock.cpsourcing.callLogs

import com.anarock.cpsourcing.entities.CallLogs

class CallLogsItem :CallLogsListItemType()
{
    private lateinit var callLogs : CallLogs
    fun setCallLogs(callLogs: CallLogs)
    {
        this.callLogs = callLogs
    }

    fun getCallLogs():CallLogs
    {
        return callLogs
    }
    override fun getType(): Int {
        return TYPE_CALL_LOG
    }

}