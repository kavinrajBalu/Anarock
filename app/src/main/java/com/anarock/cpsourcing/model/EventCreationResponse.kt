package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data  class EventCreationResponse(
    @SerializedName("id")
    @Expose
    var eventId: Int? = null,

    @SerializedName("event_type_id")
    @Expose
    var eventTypeId: Int? = null,

    @SerializedName("cp_id")
    @Expose
    var cpId: Int? = null,

    @SerializedName("lead_id")
    @Expose
    var leadId: Int? = null,

    @SerializedName("start_time")
    @Expose
    var startTime: String? = null,

    @SerializedName("reminder_time")
    @Expose
    var reminderTime: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("meta_data")
    @Expose
    var metaData: Any? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null,

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = null
)