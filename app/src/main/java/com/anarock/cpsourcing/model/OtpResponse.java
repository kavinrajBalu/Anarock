
package com.anarock.cpsourcing.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponse {

@SerializedName("expires_in")
@Expose
public Integer expiresIn;
@SerializedName("expires_at")
@Expose
public Integer expiresAt;
@SerializedName("id")
@Expose
public Integer id;
@SerializedName("email")
@Expose
public String email;
@SerializedName("name")
@Expose
public String name;
@SerializedName("phone")
@Expose
public String phone;
@SerializedName("is_random_password")
@Expose
public Boolean isRandomPassword;
@SerializedName("device_id")
@Expose
public String deviceId;
@SerializedName("is_available")
@Expose
public Boolean isAvailable;
@SerializedName("department")
@Expose
public String department;
@SerializedName("role")
@Expose
public String role;
@SerializedName("country_id")
@Expose
public Integer countryId;
@SerializedName("user_type")
@Expose
public String userType;
@SerializedName("profile_type")
@Expose
public String profileType;
@SerializedName("auth_token")
@Expose
public String authToken;

}