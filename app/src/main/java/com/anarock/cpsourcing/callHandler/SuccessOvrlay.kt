package com.anarock.cpsourcing.callHandler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.interfaces.PhoneCallStatusCallBack
import com.anarock.cpsourcing.utilities.CommonUtilities

class SuccessOvrlay : Service() {

    private var failedCallWindowManager: WindowManager? = null
    private var failedCallOverlayView: View? = null
    private var reScheduleCallWindowManager: WindowManager? = null
    private var reScheduleCallOverlayView: View? = null
    lateinit var callStateListener : CallStateListener
    lateinit var  params: WindowManager.LayoutParams

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        failedCallOverlayView = LayoutInflater.from(this).inflate(R.layout.success_call_view, null)
        reScheduleCallOverlayView = LayoutInflater.from(this).inflate(R.layout.reschedule_call_view,null)
            params  = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER //Initially view will be added to top-left corner

        //Add the view to the window
        failedCallWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        reScheduleCallWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        failedCallWindowManager!!.addView(failedCallOverlayView, params)


        failedCallOverlayView!!.findViewById<ImageView>(R.id.close)
            .setOnClickListener(View.OnClickListener {
                stopSelf()
            })

        failedCallOverlayView!!.findViewById<TextView>(R.id.done)
            .setOnClickListener(View.OnClickListener {
                stopSelf()
            })

        failedCallOverlayView!!.findViewById<Button>(R.id.call_again).setOnClickListener {

            failedCallWindowManager!!.removeView(failedCallOverlayView)

           val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

             callStateListener   = CallStateListener(object : PhoneCallStatusCallBack {
                override fun onCallSuccess() {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
                }

                override fun onCallFailed() {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
                    failedCallWindowManager!!.addView(failedCallOverlayView, params)
                }
            })
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)

            CommonUtilities.makeCall(applicationContext,"8903653203")
        }

        failedCallOverlayView!!.findViewById<TextView>(R.id.re_schedule).setOnClickListener {
            showRescheduleScreen()
        }

        reScheduleCallOverlayView!!.findViewById<ImageView>(R.id.back).setOnClickListener {
            showCallFailedScreen()
        }
    }

    private fun showCallFailedScreen() {
        failedCallWindowManager!!.removeView(reScheduleCallOverlayView)
        reScheduleCallWindowManager!!.addView(failedCallOverlayView, params)
    }

    private fun showRescheduleScreen() {
        failedCallWindowManager!!.removeView(failedCallOverlayView)
        reScheduleCallWindowManager!!.addView(reScheduleCallOverlayView, params)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (failedCallOverlayView != null) failedCallWindowManager!!.removeView(failedCallOverlayView)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        this.stopSelf()
    }
}