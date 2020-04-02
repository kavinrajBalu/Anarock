package com.anarock.cpsourcing.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.callHandler.CallStateListener
import com.anarock.cpsourcing.callHandler.EventSuccessCallOverlay
import com.anarock.cpsourcing.databinding.FragementEventBinding
import com.anarock.cpsourcing.interfaces.PhoneCallStatusCallBack
import com.anarock.cpsourcing.utilities.CommonUtilities
//import com.anarock.cpsourcing.utilities.CommonUtilities.Companion.playCall
import com.anarock.cpsourcing.utilities.Constants
import com.anarock.cpsourcing.utilities.SharedPreferenceUtil
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {

    private val loginSharedViewModel : LoginSharedViewModel by activityViewModels()
    private val sharedUtilityViewModel: SharedUtilityViewModel by activityViewModels()

    //TODO : All runtime permission goes here
    private var permissions = ArrayList<String?>()
    private val REQUEST_MULTIPLE_PERMISSION = 1
    val CLASS_NAME = EventFragment::class.java.simpleName
    lateinit var callStateListener: CallStateListener
    private val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084
    val dialogFragment =PermissionsFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragementEventBinding =  DataBindingUtil.inflate(inflater, R.layout.fragement_event,container,false)
        sharedUtilityViewModel.setToolBarVisibility(false)
        sharedUtilityViewModel.setCustomStatusBar(R.color.anarock_blue)
        dialogFragment.isCancelable = false

        val telephonyManager =
            requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        binding.addEvent.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragement_to_addNewEvent)
        }

      /*  binding.callCp.setOnClickListener {
            CommonUtilities.makeCall(requireContext(),"8903653203")

            val callRecord = CallRecord.Builder(requireContext())
                .setLogEnable(true)
                .setRecordFileName("sample")
                .setRecordDirName("8903653203")
                .setRecordDirPath(requireContext().getExternalFilesDir(null)?.absolutePath)
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .build()
            callRecord.startCallReceiver()

             callStateListener = CallStateListener(object :PhoneCallStatusCallBack{
                override fun onCallSuccess() {
                    telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_NONE)
                    Log.d(CLASS_NAME,"Call success")
                    callRecord.stopCallReceiver()
                    playCall(callRecord)
                }

                override fun onCallFailed() {
                    telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_NONE)
                    Log.d(CLASS_NAME,"Call failed")
                    requireActivity().startService(Intent(requireContext(), EventSuccessCallOverlay::class.java))
                }

            })
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }*/

        binding.addCp.setOnClickListener {
            findNavController().navigate(R.id.action_eventFragement_to_addCpFragment, bundleOf("editMode" to false))
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()
        getAllRequiredPermission()
        val token = SharedPreferenceUtil.getInstance(requireContext()).getString(Constants.PreferenceKeys.TOKEN, "")
       if(CommonUtilities.notnull(token)){
           if (permissions.isNotEmpty() || !CommonUtilities.isPackageInstalled(
                   Constants.CONNECT_APP_PACKAGE_NAME,
                   requireContext()
               )
           ) {
               openPermissionDialogFrag(false)

           }else{
               sharedUtilityViewModel.setToolBarVisibility(false)
               sharedUtilityViewModel.setCustomStatusBar(R.color.anarock_blue)
               sharedUtilityViewModel.setBottomNavigationVisibility(true)
           }
       }else{
           if (permissions.isNotEmpty() || !CommonUtilities.isPackageInstalled(
                   Constants.CONNECT_APP_PACKAGE_NAME,
                   requireContext()
               )
           ) {
               sharedUtilityViewModel.setBottomNavigationVisibility(false)
               openPermissionDialogFrag(true)

           } else {
               sharedUtilityViewModel.setBottomNavigationVisibility(false)
               findNavController().navigate(R.id.action_eventFragement_to_loginNavigation)
           }
       }

    }

    private fun openPermissionDialogFrag(isFullScreen: Boolean) {
        var bundle = bundleOf("fullScreen" to isFullScreen)

        dialogFragment.arguments = bundle

        var ft  = requireActivity().supportFragmentManager.beginTransaction()
        val prev: Fragment? =
            requireActivity().supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)


        dialogFragment.show(ft, "dialog")    }

    private fun getAllRequiredPermission() {
        permissions.clear()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)

        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CALL_PHONE)

        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.RECORD_AUDIO)

        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.READ_CONTACTS)

        }

    }


}
