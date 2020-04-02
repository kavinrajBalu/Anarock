package com.anarock.cpsourcing.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CpFormData {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("phone_country_id")
    @Expose
    var phoneCountryId: String? = null

    @SerializedName("firm_name")
    @Expose
    var firmName: String? = null

    @SerializedName("alternate_phone_one")
    @Expose
    var alternatePhoneOne: String? = null

    @SerializedName("alternate_phone_one_country_id")
    @Expose
    var alternatePhoneOneCountryId: String? = null

    @SerializedName("alternate_phone_two")
    @Expose
    var alternatePhoneTwo: String? = null

    @SerializedName("alternate_phone_two_country_id")
    @Expose
    var alternatePhoneTwoCountryId: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("locality")
    @Expose
    var locality: String? = null

    @SerializedName("nature_of_business")
    @Expose
    var natureOfBusiness: String? = null

    @SerializedName("organisation_type")
    @Expose
    var organisationType: String? = null

    @SerializedName("members")
    @Expose
    var members: String? = null

    @SerializedName("gst")
    @Expose
    var gst: String? = null

    @SerializedName("rera")
    @Expose
    var rera: String? = null

    @SerializedName("team_size")
    @Expose
    var teamSize: String? = null

    @SerializedName("area")
    @Expose
    var area: String? = null

    var partnerList =  ArrayList<PartnerFormData>()
}