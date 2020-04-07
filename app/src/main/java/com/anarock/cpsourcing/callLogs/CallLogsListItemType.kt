package com.anarock.cpsourcing.callLogs

abstract class CallLogsListItemType {
    public val TYPE_DATE :Int = 0
    public val TYPE_CALL_LOG :Int = 1

    abstract fun getType():Int
}