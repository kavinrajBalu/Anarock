package com.anarock.cpsourcing.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentOtpBinding
import com.anarock.cpsourcing.model.ToolBarTheme
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.utilities.SMSRetrieverClient
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import kotlinx.android.synthetic.main.fragment_otp.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [OtpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OtpFragment : Fragment() {
    private var otp: String = ""
    var phoneNo: String = ""
    var countryId: Int = 0

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val loginSharedViewModel: LoginSharedViewModel by activityViewModels()
    lateinit var mOTPOne: EditText
    lateinit var mOTPTwo: EditText
    lateinit var mOTPThree: EditText
    lateinit var mOTPFour: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOtpBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_otp, container, false
        )
        loginSharedViewModel.setToolbarTheme(ToolBarTheme(true, false))
        mOTPOne = binding.otpOne
        mOTPTwo = binding.otpTwo
        mOTPThree = binding.otpThree
        mOTPFour = binding.otpFour

        phoneNo = arguments?.getString("phone")!!
        countryId = arguments?.getInt("countryId")!!
        binding.otpPhoneNo.text = phoneNo


        loginSharedViewModel.getOTP().observe(viewLifecycleOwner, Observer {
            if(CommonUtilities.notnull(it)){
                mOTPOne.setText(it[0].toString())
                mOTPTwo.setText(it[1].toString())
                mOTPThree.setText(it[2].toString())
                mOTPFour.setText(it[3].toString())


            }
        })
        binding.verifyOtpButton.setOnClickListener {
            validateOTP()
        }
        binding.resendButton.setOnClickListener {
            if (CommonUtilities.notnull(phoneNo)) {
                loginSharedViewModel.loginTriggerOTP(countryId, phoneNo)
                    .observe(viewLifecycleOwner, Observer {

                    })
            }
            setUpCountDownTimer(it)
        }

        binding.otpEditButton.setOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_loginFragment)
        }

        binding.otpGetSupportTextView.setOnClickListener {
            CommonUtilities.getEmailSupport(requireContext())
        }

        setUpOtpTextListeners()

        return binding.root
    }

    private fun setUpCountDownTimer(resendButton: View) {
        var counter = 30
        resendButton.visibility = View.GONE
        counttime.visibility = View.VISIBLE
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counttime.text = getString(R.string.time_format_string) + String.format("%02d", counter)
                counter--
            }

            override fun onFinish() {
                resendButton.visibility = View.VISIBLE
                counttime.visibility = View.GONE
            }
        }.start()    }

    private fun setUpOtpTextListeners() {

        mOTPOne.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count == 1) {
                    mOTPTwo.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mOTPTwo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count == 1) {
                    mOTPThree.requestFocus()
                } else if (count == 0) {
                    mOTPOne.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mOTPThree.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count == 1) {
                    mOTPFour.requestFocus()
                } else if (count == 0) {
                    mOTPTwo.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        mOTPFour.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (count == 1) {
                    validateOTP()
                } else if (count == 0) {
                    mOTPThree.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun validateOTP() {
        mOTPOne.clearFocus()
        mOTPTwo.clearFocus()
        mOTPThree.clearFocus()
        mOTPFour.clearFocus()
        val one = mOTPOne.text.toString()
        val two = mOTPTwo.text.toString()
        val three = mOTPThree.text.toString()
        val four = mOTPFour.text.toString()
        val vibrate =
            AnimationUtils.loadAnimation(context, R.anim.vibrate)
        if (!CommonUtilities.notnull(one) || !CommonUtilities.notnull(two) || !CommonUtilities.notnull(
                three
            ) || !CommonUtilities.notnull(four)
        ) {
            mOTPOne.startAnimation(vibrate)
            mOTPTwo.startAnimation(vibrate)
            mOTPThree.startAnimation(vibrate)
            mOTPFour.startAnimation(vibrate)
        } else {
            otp = one + two + three + four

            if (CommonUtilities.notnull(otp)) {
                loginSharedViewModel.verifyOTP(otp, countryId, phoneNo)
                    .observe(viewLifecycleOwner, Observer {
                        findNavController().navigate(R.id.action_global_eventFragement)
                        loginSharedViewModel.setBottomNavigationVisibility(true)
                        loginSharedViewModel.setLoginState(LoginSharedViewModel.LoginState.LOGIN_SUCCESS)
                    })
            }

        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OtpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OtpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }


    }
}
