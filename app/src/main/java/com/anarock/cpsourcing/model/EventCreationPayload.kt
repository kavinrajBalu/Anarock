package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class EventCreationPayload(
    @SerializedName("event_type_id")
    @Expose
    var eventTypeId: Int? = null,

    @SerializedName("cp_id")
    @Expose
    var cpId: Int? = null,

    @SerializedName("start_time")
    @Expose
    var startTime: String? = null,

    @SerializedName("reminder_time")
    @Expose
    var reminderTime: String? = null,

    @SerializedName("lead_id")
    @Expose
    var leadId: Int? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = null,

    @SerializedName("note")
    @Expose
    var note: String? = null
)