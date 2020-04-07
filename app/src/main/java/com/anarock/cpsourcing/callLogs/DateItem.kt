package com.anarock.cpsourcing.callLogs

class DateItem : CallLogsListItemType()
{
   private lateinit var date:String
    private lateinit var dateTime :String
    override fun getType(): Int {
        return TYPE_DATE
    }
    fun setDate(date : String)
    {
        this.date = date
    }

    fun getDate():String
    {
        return date
    }

    fun setDateTime(dateTime : String)
    {
        this.dateTime = dateTime
    }

    fun getDateTime():String
    {
        return dateTime
    }
}