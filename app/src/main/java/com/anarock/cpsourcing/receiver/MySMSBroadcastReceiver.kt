package com.anarock.cpsourcing.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.anarock.cpsourcing.fragments.OtpFragment
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import java.util.regex.Matcher
import java.util.regex.Pattern


class MySMSBroadcastReceiver : BroadcastReceiver() {
    var p: Pattern = Pattern.compile("\\d{4}")

    override fun onReceive(context: Context?, intent: Intent?) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION == intent?.action){
            val extras = intent.extras
            val status: Status? = extras!![SmsRetriever.EXTRA_STATUS] as Status?

            when (status!!.statusCode) {
                CommonStatusCodes.SUCCESS ->  {
                    // Get SMS message contents
                    var  message : String?= extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?

                    val m: Matcher = p.matcher(message)
                    Log.d("SMS:", message)
                    while(m.find()) {
                        Log.d("OTP:", m.group())
                        LoginSharedViewModel.setOTP(m.group())
                    }

                }
                CommonStatusCodes.TIMEOUT -> {
                }
            }
        }
    }
}