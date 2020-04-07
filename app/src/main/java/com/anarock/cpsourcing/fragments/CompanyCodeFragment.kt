package com.anarock.cpsourcing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentCompanyCodeBinding
import com.anarock.cpsourcing.model.ToolBarTheme
import com.anarock.cpsourcing.retrofit.ApiClient
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompanyCodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyCodeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val viewModel: LoginSharedViewModel by activityViewModels()


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
        val binding: FragmentCompanyCodeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_company_code, container, false)

//        requireContext.reqWindow.FEATURE_NO_TITLE);

        viewModel.setToolbarTheme(ToolBarTheme(true, true))

        binding.nextImageButton.setOnClickListener {
            var companyName: String = binding.ccEditText.text.toString()
            if (companyName != null && companyName.isNotEmpty()) {
                ApiClient.resetRetrofit()
                ApiClient.setDomainName(ApiClient.META_DOMAIN, ApiClient.DOMAIN_ANAROCK);
                viewModel.fetchTenantDomain(companyName).observe(viewLifecycleOwner, Observer {
                    if (it.message.equals("Success", ignoreCase = true)) {
                        val domain = it.response!!.domain
                        ApiClient.setDomainName(ApiClient.EMPLOYEE, domain)
                        ApiClient.resetRetrofit()
                        var bundle = bundleOf("tenantName" to companyName)
                        findNavController().navigate(
                            R.id.action_companyCode_to_loginFragment,
                            bundle
                        )
                    }
                })
            } else {
                Toast.makeText(context, "Please enter a valid company code", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.ccGetSupportTextView.setOnClickListener {
            CommonUtilities.getEmailSupport(requireContext())
        }

        val callback  = requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
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
         * @return A new instance of fragment CompanyCode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompanyCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
