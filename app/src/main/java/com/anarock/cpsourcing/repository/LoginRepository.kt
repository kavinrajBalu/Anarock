package com.anarock.cpsourcing.repository

import android.content.Context
import android.util.Log
import com.anarock.cpsourcing.model.TenantDomainResponseModel
import com.anarock.cpsourcing.retrofit.ApiClient
import com.anarock.cpsourcing.utilities.NetworkUtil
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val mContext: Context?){
        private val TAG = "LoginRepository"

    fun fetchTenantConfig(tenantName: String, loginResponseStatus: LoginResponseStatus) {
        if (NetworkUtil.isInternetConnected(mContext)) {

            fetchTenantDomainConfig(tenantName, loginResponseStatus)

        } else {
            Log.e(TAG, "Check your internet connection!");
        }
    }

        fun fetchTenantDomainConfig(tenantName: String, loginResponseStatus: LoginResponseStatus) {
            val apiService = ApiClient.getService();
            val call = apiService!!.getTenantDomain(tenantName)
            call.enqueue(object : Callback<TenantDomainResponseModel> {
                override fun onResponse(
                    call: Call<TenantDomainResponseModel>,
                    response: Response<TenantDomainResponseModel>
                ) {
                    if (response.code() === 200) {
                        val tenantDomainResponseModel: TenantDomainResponseModel? = response.body()
                        /*     PreferenceUtil.getInstance(mContext!!).putString(AUTHORIZATION_KEY,loginResponseModel!!.authToken)
                    PreferenceUtil.getInstance(mContext).putString(USER_ID,loginResponseModel.userId)*/
                        loginResponseStatus.responseStatus(LoginSharedViewModel.LoginState.LOGIN_SUCCESS)
                    } else {
                        loginResponseStatus.responseStatus(LoginSharedViewModel.LoginState.LOGIN_FAILED)
                    }
                }

                override fun onFailure(call: Call<TenantDomainResponseModel>, t: Throwable) {
                    Log.e(TAG, "Failed to login")
                }
            })
        }

        interface LoginResponseStatus {
            fun responseStatus(loginState: LoginSharedViewModel.LoginState)
        }

}
