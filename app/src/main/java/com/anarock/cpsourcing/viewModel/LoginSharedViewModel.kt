package com.anarock.cpsourcing.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anarock.cpsourcing.repository.LoginRepository

class LoginSharedViewModel : ViewModel()
 {

     private var bottomNavigationVisibility : MutableLiveData<Boolean>  = MutableLiveData()
     private var loginState : MutableLiveData<LoginState>  = MutableLiveData()
     private var mLoginRepository: LoginRepository? = null


     enum class LoginState
     {
        LOGIN_SUCCESS,
         LOGIN_FAILED
     }

     init {

         loginState.value = LoginState.LOGIN_FAILED
     }

     private fun fetchTenantDomain() {
             getLoginRepository().fetchTenantConfig("Anarock", object : LoginRepository.LoginResponseStatus {
                     override fun responseStatus(loginstate: LoginState) {
                         if (loginstate == LoginState.LOGIN_SUCCESS) {

                         } else {

                         }
                     }
                 })
     }


     fun  setBottomNavigationVisibility(state : Boolean)
     {
        bottomNavigationVisibility.value = state
     }

     fun getBottomNavigationVisibility():LiveData<Boolean>
     {
         return  bottomNavigationVisibility
     }

     fun setLoginState(loginstate : LoginState)
     {
         this.loginState.value = loginstate
     }

     fun getLoginState():LiveData<LoginState>
     {
         return loginState
     }
     private fun getLoginRepository(): LoginRepository {
         if(mLoginRepository == null){
             mLoginRepository = LoginRepository(mActivity!!.get())
         }
         return mLoginRepository as LoginRepository
     }

 }