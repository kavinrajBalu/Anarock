package com.anarock.cpsourcing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentCompanyCodeBinding
import com.anarock.cpsourcing.retrofit.ApiClient
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompanyCode.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompanyCode : Fragment() {
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

        binding.nextImageButton.setOnClickListener {
            var companyName: String = binding.ccEditText.text.toString()
            if (companyName!=null && companyName.isNotEmpty()) {
                viewModel.fetchTenantDomain(companyName).observe(viewLifecycleOwner, Observer {
                    if (it.message.equals("Success", ignoreCase = true)) {
                        val domain = it.response!!.domain
                        ApiClient.setDomainName(ApiClient.EMPLOYEE_DOMAIN, domain);
                        ApiClient.resetRetrofit()
                        var bundle = bundleOf("tenantName" to companyName)
                        findNavController().navigate(R.id.action_companyCode_to_loginFragment, bundle)
                    }
                })
            } else {
                Toast.makeText(context, "Please enter a valid company code", Toast.LENGTH_LONG)
            }
        }
        return binding.root
    }

}
