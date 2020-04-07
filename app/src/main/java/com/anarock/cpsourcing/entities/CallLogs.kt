package com.anarock.cpsourcing.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_logs")
data class CallLogs(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "time") val dateTime: String?,
    @ColumnInfo(name="cp_id") val cpId : Int,
    @ColumnInfo(name = "date") val date :String,
    @ColumnInfo(name="lead_id") val leadId : Int,
    @ColumnInfo(name = "call_record_path") val callRecordPath: String?,
    @ColumnInfo(name = "incoming") val incoming: Boolean?
)