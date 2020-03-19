package com.anarock.cpsourcing.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.callHandler.CallStateListener
import com.anarock.cpsourcing.callHandler.FailedCallOverlay
import com.anarock.cpsourcing.databinding.FragementEventBinding
import com.anarock.cpsourcing.interfaces.PhoneCallStatusCallBack
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [EventFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EventFragment : Fragment() {

    private val loginSharedViewModel : LoginSharedViewModel by activityViewModels()

    //TODO : All runtime permission goes here
    private  var permissions : Array<String?>  = arrayOfNulls(3)
    private val REQUEST_MULTIPLE_PERMISSION = 1
    val CLASS_NAME = EventFragment::class.java.simpleName
    lateinit var callStateListener : CallStateListener
    private val CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragementEventBinding =  DataBindingUtil.inflate(inflater, R.layout.fragement_event,container,false)

        // TODO : For understanding purpose managing login state using viewModel. Use preference to maintain login/logout state.
        loginSharedViewModel.getLoginState().observe(viewLifecycleOwner, Observer {
            if(it == LoginSharedViewModel.LoginState.LOGIN_FAILED) {
                loginSharedViewModel.setBottomNavigationVisibility(false)
                findNavController().navigate(R.id.action_eventFragement_to_loginNavigation)
            }
        })

        val telephonyManager = requireActivity().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        getAllRequiredPermission()

        binding.callCp.setOnClickListener {

            CommonUtilities.makeCall(requireContext(),"8903653203")

             callStateListener = CallStateListener(object :PhoneCallStatusCallBack{
                override fun onCallSuccess() {
                    telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_NONE)
                    Log.d(CLASS_NAME,"Call success")
                }

                override fun onCallFailed() {
                    telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_NONE)
                    Log.d(CLASS_NAME,"Call failed")
                    requireActivity().startService(Intent(requireContext(), FailedCallOverlay::class.java))

                }

            })
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        }

        return  binding.root

    }

    private fun getAllRequiredPermission() {
        when {
            ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED -> {
                permissions[0] = Manifest.permission.READ_PHONE_STATE
            }
            ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ->
            {
                permissions[1] = Manifest.permission.CALL_PHONE
            }
            else -> {

            }
        }

        requestPermissions(permissions,REQUEST_MULTIPLE_PERMISSION)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_MULTIPLE_PERMISSION -> {
              /*  val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${requireContext().packageName}")
                )
                startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION)*/
            }
            else -> {

            }
        }
    }
}
