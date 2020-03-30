package com.anarock.cpsourcing.callHandler

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.anarock.cpsourcing.R

class EventSuccessCallOverlay : Service() {

    private lateinit var successCallWindowManager: WindowManager
    private lateinit var successCallOverlayView: View
    lateinit var  params: WindowManager.LayoutParams
    lateinit var visitProposed : Button
    lateinit var followUp : Button
    lateinit var faceToFace : Button
    lateinit var notes: EditText
    private val EVENT_CREATION_BROADCAST = "action.event.creation"

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        successCallOverlayView = View.inflate(ContextThemeWrapper(this, R.style.AppTheme), R.layout.event_successful_overlay,null)
        getAllViewReferences()
        clickListeners()
        params  = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.CENTER //Initially view will be added to top-left corner

        //Add the view to the window
        successCallWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        successCallWindowManager.addView(successCallOverlayView, params)
    }

    private fun clickListeners() {
        visitProposed.setOnClickListener {
            if(notes.text.isEmpty())
            {
                notes.error = "Enter call notes"
            }
            else
            {
              val localBroadcastManager = LocalBroadcastManager.getInstance(this)
              val intent = Intent(EVENT_CREATION_BROADCAST)
                intent.putExtra("screen","proposed")
                localBroadcastManager.sendBroadcast(intent)
                stopSelf()
            }
        }
    }

    private fun getAllViewReferences() {

        visitProposed = successCallOverlayView.findViewById(R.id.visit_proposed)
        followUp = successCallOverlayView.findViewById(R.id.follow_up)
        faceToFace = successCallOverlayView.findViewById(R.id.face_to_face)
        notes = successCallOverlayView.findViewById(R.id.notes)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (successCallOverlayView != null) successCallWindowManager.removeView(successCallOverlayView)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        this.stopSelf()
    }
}