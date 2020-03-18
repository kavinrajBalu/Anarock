package com.anarock.cpsourcing.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginSharedViewModel : ViewModel()
 {

     private var bottomNavigationVisibility : MutableLiveData<Boolean>  = MutableLiveData()
     private var loginState : MutableLiveData<LoginState>  = MutableLiveData()

     enum class LoginState
     {
        LOGIN_SUCCESS,
         LOGIN_FAILED
     }

     init {

         loginState.value = LoginState.LOGIN_SUCCESS
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

 }