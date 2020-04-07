package com.anarock.cpsourcing.callHandler

import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.anarock.cpsourcing.interfaces.PhoneCallStatusCallBack
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.utilities.DateTimeUtils.getSecondsDifference
import java.util.*

class CallStateListener(private val phoneCallStatusCallBack: PhoneCallStatusCallBack) : PhoneStateListener() {


    val CLASS_NAME = CallStateListener::class.java.simpleName

    private var startTime : Date? = null
    /**
     * TODO Call back method to identify calls
     *
     * @param state
     * @param phoneNumber
     */
    override fun onCallStateChanged(state: Int, incomingNumber: String?) {
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            Log.d(CLASS_NAME,"Phone Ringing")
        }
        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
            Log.d(CLASS_NAME,"Phone is currently in a call")
            startTime = Calendar.getInstance().time
        }
        if (state == TelephonyManager.CALL_STATE_IDLE) {
            startTime?.let {
                //Log.d(CLASS_NAME,"Duration of the call ${CommonUtilities.getSecondsDifference(startTime!!,endtime = Calendar.getInstance().time)}")
                if(getSecondsDifference(startTime!!,endtime = Calendar.getInstance().time) > 5)
                {
                    phoneCallStatusCallBack.onCallSuccess()
                }
                else
                {
                    phoneCallStatusCallBack.onCallFailed()
                }
            }?:Log.d(CLASS_NAME,"phone is neither ringing nor in a call")
        }
    }

}