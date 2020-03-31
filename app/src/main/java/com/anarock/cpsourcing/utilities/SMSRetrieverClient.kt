package com.anarock.cpsourcing.utilities

import android.content.Context
import android.content.IntentFilter
import com.anarock.cpsourcing.receiver.MySMSBroadcastReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever


class SMSRetrieverClient {
    companion object {
        fun startSMSRetriever(mContext: Context) {
            var client = SmsRetriever.getClient(mContext)

            // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
            // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
            // action SmsRetriever#SMS_RETRIEVED_ACTION.
            val task = client.startSmsRetriever()

            task.addOnSuccessListener {
                // Successfully started retriever, expect broadcast intent
               /* val filter = IntentFilter()
                filter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
                val receiver =  MySMSBroadcastReceiver()
                mContext.registerReceiver(receiver, filter)*/
            }
            task.addOnFailureListener {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }

        }

    }


}
