package com.anarock.cpsourcing.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponseModel {

@SerializedName("response")
@Expose
public OtpResponse response;
@SerializedName("message")
@Expose
public String message;

}