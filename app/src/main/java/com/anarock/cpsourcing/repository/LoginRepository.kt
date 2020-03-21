package com.anarock.cpsourcing.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.model.*
import com.anarock.cpsourcing.retrofit.ApiClient
import com.anarock.cpsourcing.retrofit.ApiService
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {
    companion object {
        private val TAG = "LoginRepository"
        private  var apiService: ApiService?= ApiClient.getService()


        fun fetchTenantConfig(
            mContext: Context,
            tenantName: String
        ): MutableLiveData<TenantDomainResponseModel> {
            apiService = ApiClient.getService()!!         //to apply changes in domain name
            var tenantDomainResponseModel: MutableLiveData<TenantDomainResponseModel> =
                MutableLiveData()
            if (CommonUtilities.isNetworkConnected(mContext)) {
                val call = apiService!!.getTenantDomain(tenantName)
                call.enqueue(object : Callback<TenantDomainResponseModel> {
                    override fun onResponse(
                        call: Call<TenantDomainResponseModel>,
                        response: Response<TenantDomainResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            tenantDomainResponseModel.value = response.body()

                        } else{
                            Toast.makeText(mContext, "Invalid company name", Toast.LENGTH_LONG)
                        }
                    }
                    override fun onFailure(call: Call<TenantDomainResponseModel>, t: Throwable) {
                        Log.e(TAG, "Failed to fetch company domain")
                    }
                })
            }
            return tenantDomainResponseModel
        }

        fun fetchCountryCodes(
            mContext: Context
        ): MutableLiveData<CountryResponseModel> {
            var countryCodeResponseModel: MutableLiveData<CountryResponseModel> =
                MutableLiveData()
            if (CommonUtilities.isNetworkConnected(mContext)) {
                val call = apiService!!.getCountryCodes()
                call.enqueue(object : Callback<CountryResponseModel> {
                    override fun onResponse(
                        call: Call<CountryResponseModel>,
                        response: Response<CountryResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            countryCodeResponseModel.value = response.body()

                        } else{
                            Toast.makeText(mContext, "Failed to fetch country codes", Toast.LENGTH_LONG)
                        }
                    }
                    override fun onFailure(call: Call<CountryResponseModel>, t: Throwable) {
                        Log.e(TAG, "Failed to fetch country codes")
                    }
                })
            }
            return countryCodeResponseModel
        }


        fun triggerOTP(
            mContext: Context,
            countryId: Int, phoneNo : String
        ): MutableLiveData<LoginResponseModel> {
            apiService = ApiClient.getService()!!
            var loginResponseModel: MutableLiveData<LoginResponseModel> =
                MutableLiveData()
            if (CommonUtilities.isNetworkConnected(mContext)) {

                val loginRequest = LoginRequestModel(phoneNo, countryId)
                val bodyJson: String? = Gson().toJson(loginRequest)

                val requestBody: RequestBody = RequestBody.create(
                    okhttp3.MediaType.parse(
                        mContext.getString(
                            R.string.request_type
                        )
                    ), bodyJson
                )

                val call = apiService!!.loginUser(requestBody)
                call.enqueue(object : Callback<LoginResponseModel> {
                    override fun onResponse(
                        call: Call<LoginResponseModel>,
                        response: Response<LoginResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            loginResponseModel.value = response.body()

                        } else{
                            Toast.makeText(mContext, "Invalid phone number", Toast.LENGTH_LONG)
                        }
                    }
                    override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                        Log.e(TAG, "Failed to trigger OTP")
                    }
                })
            }
            return loginResponseModel
        }

        fun verifyOTP(
            mContext: Context,
            countryId: Int, phoneNo : String, otp : String
        ): MutableLiveData<OtpResponseModel> {
            apiService = ApiClient.getService()!!
            var otpResponseModel: MutableLiveData<OtpResponseModel> =
                MutableLiveData()
            if (CommonUtilities.isNetworkConnected(mContext)) {

                val loginRequest = OtpRequestModel(phoneNo, countryId, otp)
                val bodyJson: String? = Gson().toJson(loginRequest)

                val requestBody: RequestBody = RequestBody.create(
                    okhttp3.MediaType.parse(
                        mContext.getString(
                            R.string.request_type
                        )
                    ), bodyJson
                )

                val call = apiService!!.verifyOtp(requestBody)
                call.enqueue(object : Callback<OtpResponseModel> {
                    override fun onResponse(
                        call: Call<OtpResponseModel>,
                        response: Response<OtpResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            otpResponseModel.value = response.body()

                        } else{
                            Toast.makeText(mContext, "Invalid OTP", Toast.LENGTH_LONG)
                        }
                    }
                    override fun onFailure(call: Call<OtpResponseModel>, t: Throwable) {
                        Log.e(TAG, "Failed to verify OTP")
                    }
                })
            }
            return otpResponseModel
        }

    }
}
