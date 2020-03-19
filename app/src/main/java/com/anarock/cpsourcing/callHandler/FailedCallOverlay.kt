package com.anarock.cpsourcing.callHandler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.interfaces.PhoneCallStatusCallBack
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.w3c.dom.Text


class FailedCallOverlay : Service() {

    private lateinit var failedCallWindowManager: WindowManager
    private lateinit var failedCallOverlayView: View
    private lateinit var reScheduleCallWindowManager: WindowManager
    private lateinit var reScheduleCallOverlayView: View
    lateinit var callStateListener : CallStateListener
    lateinit var  params: WindowManager.LayoutParams
    // schedule screen
    /*private lateinit var alternateTimeOne  : Chip
    private lateinit var alternateTimeTwo  : Chip
    private lateinit var alternateTimeThree  : Chip
    private lateinit var alternateTimeFour  : Chip
    private lateinit var alternateTimeFive  : Chip
    private lateinit var alternateTimeSix  : Chip*/
    private lateinit var timePickerGroup : ChipGroup
    private lateinit var confirmRescheduleTime  : TextView
    private lateinit var reScheduleBack : ImageView

    //call failed screen
    private lateinit var close : ImageView
    private lateinit var confirmSchedule : TextView
    private lateinit var rescheduleTime : TextView
    private lateinit var callAgain : Button


    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        failedCallOverlayView = View.inflate(ContextThemeWrapper(this, R.style.AppTheme), R.layout.failed_call_view,null)
        reScheduleCallOverlayView = View.inflate(ContextThemeWrapper(this, R.style.AppTheme), R.layout.reschedule_call_view,null)
        getAllViewReferences()
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
        failedCallWindowManager.addView(failedCallOverlayView, params)

            close.setOnClickListener(View.OnClickListener {
                stopSelf()
            })

        confirmSchedule.findViewById<TextView>(R.id.done)
            .setOnClickListener(View.OnClickListener {
                stopSelf()
            })

       callAgain.setOnClickListener {

            failedCallWindowManager.removeView(failedCallOverlayView)

           val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

             callStateListener   = CallStateListener(object : PhoneCallStatusCallBack {
                override fun onCallSuccess() {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
                }

                override fun onCallFailed() {
                    telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
                    failedCallWindowManager.addView(failedCallOverlayView, params)
                }
            })
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)

            CommonUtilities.makeCall(applicationContext,"8903653203")
        }

        rescheduleTime.setOnClickListener {
            showRescheduleScreen()
        }

        reScheduleBack.setOnClickListener {
            showCallFailedScreen()
        }

        confirmRescheduleTime.setOnClickListener {
            showCallFailedScreen()
        }

        timePickerGroup.setOnCheckedChangeListener { chipGroup, id ->
            val chip = chipGroup.findViewById<Chip>(id)

        }



    }

    private fun getAllViewReferences() {

        //Failed call screen views
        close = failedCallOverlayView.findViewById(R.id.close)
        confirmSchedule = failedCallOverlayView.findViewById(R.id.done)
        callAgain = failedCallOverlayView.findViewById(R.id.call_again)
        rescheduleTime = failedCallOverlayView.findViewById(R.id.re_schedule)
        // Reschedule screen views
       /* alternateTimeOne = reScheduleCallOverlayView.findViewById(R.id.alternate_time_one)
        alternateTimeTwo = reScheduleCallOverlayView.findViewById(R.id.alternate_time_two)
        alternateTimeThree = reScheduleCallOverlayView.findViewById(R.id.alternate_time_three)
        alternateTimeFour = reScheduleCallOverlayView.findViewById(R.id.alternate_time_four)
        alternateTimeFive = reScheduleCallOverlayView.findViewById(R.id.alternate_time_five)
        alternateTimeSix = reScheduleCallOverlayView.findViewById(R.id.alternate_time_six)*/
        timePickerGroup = reScheduleCallOverlayView.findViewById(R.id.chipGroup)
        reScheduleBack = reScheduleCallOverlayView.findViewById(R.id.back)
        confirmRescheduleTime = reScheduleCallOverlayView.findViewById(R.id.conform_schedule)
    }

    private fun showCallFailedScreen() {
        failedCallWindowManager.removeView(reScheduleCallOverlayView)
        reScheduleCallWindowManager.addView(failedCallOverlayView, params)
    }

    private fun showRescheduleScreen() {
        failedCallWindowManager.removeView(failedCallOverlayView)
        reScheduleCallWindowManager.addView(reScheduleCallOverlayView, params)
        //Alternate time one will be default.
        //setViewSelectedBlueBackground(alternateTimeOne)

    }

    private fun setViewSelectedBlueBackground(view: TextView?) {
        view?.background = getDrawable(R.drawable.blue_button_shape)
        view?.setPadding(resources.getDimension(R.dimen._20dp).toInt(),view.paddingTop,resources.getDimension(R.dimen._20dp).toInt(),view.paddingBottom)

    }


    override fun onDestroy() {
        super.onDestroy()
        if (failedCallOverlayView != null) failedCallWindowManager.removeView(failedCallOverlayView)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        this.stopSelf()
    }
}