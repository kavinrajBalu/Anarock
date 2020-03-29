package com.anarock.cpsourcing.fragments

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentLoginBinding
import com.anarock.cpsourcing.model.CountryCodeModel
import com.anarock.cpsourcing.model.ToolBarTheme
import com.anarock.cpsourcing.receiver.MySMSBroadcastReceiver
import com.anarock.cpsourcing.retrofit.ApiClient
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.utilities.SMSRetrieverClient
import com.anarock.cpsourcing.utilities.SMSRetrieverClient.Companion.startSMSRetriever
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import com.google.android.gms.auth.api.phone.SmsRetriever

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val loginSharedViewModel: LoginSharedViewModel by activityViewModels()
    val countryCodeList = ArrayList<String>()
    private var countriesList = ArrayList<CountryCodeModel>()


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

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )

        val companyNameTv = binding.loginCcName
        val spinner: Spinner = binding.countryCodeSpinner
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryCodeList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var countryId = 49

        companyNameTv.text = arguments?.getString("tenantName")

        startSMSRetriever(requireContext())

        loginSharedViewModel.setToolbarTheme(ToolBarTheme(true, false))

        if (spinner != null) {
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, position: Int, id: Long
                ) {
                    countryId = countriesList[position].id
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }

        loginSharedViewModel.fetchCountryCodes().observe(viewLifecycleOwner, Observer {
            countriesList = it.countries
            for (i in countriesList) {
                i.countryCode?.let { it1 -> countryCodeList.add(it1) }
            }

            adapter.notifyDataSetChanged()

        })

        binding.loginEditButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_companyCode)
        }

        binding.loginEditText.addTextChangedListener(object : TextWatcher {
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
                if (CommonUtilities.isValidPhoneNumber(s.toString())) {
                    binding.loginErrorMsg.text=""
                } else {
                    binding.loginErrorMsg.text=getString(R.string.valid_num_error_msg)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding.triggerOtpButton.setOnClickListener {
            var phoneNo: String = binding.loginEditText.text.toString()

            var bundle = Bundle()
            bundle.putString("phone", phoneNo)
            bundle.putInt("countryId", countryId)
            if (CommonUtilities.notnull(phoneNo) && CommonUtilities.isValidPhoneNumber(phoneNo)) {
                loginSharedViewModel.loginTriggerOTP(countryId, phoneNo)
                    .observe(viewLifecycleOwner, Observer {
                        findNavController().navigate(R.id.action_loginFragment_to_otpFragment, bundle)
                    })
            } else {
                Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_LONG).show()
                binding.loginErrorMsg.text = getString(R.string.valid_num_error_msg)
            }

        }

        binding.loginGetSupportTextView.setOnClickListener {
            CommonUtilities.getEmailSupport(requireContext())
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}
