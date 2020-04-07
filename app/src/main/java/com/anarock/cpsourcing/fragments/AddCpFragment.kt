package com.anarock.cpsourcing.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.adapter.CpPartnersListAdapter
import com.anarock.cpsourcing.databinding.FragmentAddCpBinding
import com.anarock.cpsourcing.interfaces.onClickEventListener
import com.anarock.cpsourcing.model.CountryCodeModel
import com.anarock.cpsourcing.model.CpFormData
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.model.PartnerFormData
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.utilities.CreateCpDialogUtil
import com.anarock.cpsourcing.utilities.EditCpDialogeUtil
import com.anarock.cpsourcing.viewModel.ChannelPartnerViewModel
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_add_cp.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddCpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCpFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isEditMode: Boolean = false
    private val sharedUtilityViewModel: SharedUtilityViewModel by activityViewModels()
    private lateinit var binding: FragmentAddCpBinding
    private val cpViewModel: ChannelPartnerViewModel by activityViewModels()
    private val loginSharedViewModel: LoginSharedViewModel by activityViewModels()
    val countryCodeList = ArrayList<String>()
    private var countriesList = ArrayList<CountryCodeModel>()
    private var partnersList = ArrayList<PartnerFormData>()
    private var cpFormData = CpFormData()
    private lateinit var partnersListAdapter: CpPartnersListAdapter


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
        binding = inflate(inflater, R.layout.fragment_add_cp, container, false)

        isEditMode = arguments?.getBoolean("editMode")!!


        binding.cpEditBtn.visibility = if (isEditMode) View.VISIBLE else View.GONE

        if (!isEditMode) {
            showAddCpView()
        }
        sharedUtilityViewModel.setBottomNavigationVisibility(false)

        loginSharedViewModel.fetchCountryCodes().observe(viewLifecycleOwner, Observer {
            countriesList = it.countries
            CreateCpDialogUtil.setCountryCodeArray(countryCodeList)
            EditCpDialogeUtil.setCountryCodeArray(countryCodeList)
            for (i in countriesList) {
                i.countryCode?.let { it1 -> countryCodeList.add(it1) }
            }
        })

        sharedUtilityViewModel.setCustomToolBar(
            CustomAppBar(
                R.color.anarock_blue,
                android.R.color.white
            )
        )
        partnersListAdapter = CpPartnersListAdapter(object : onClickEventListener {
            override fun clickEvent(action: Int, mObj: Any?, position: Int) {
                EditCpDialogeUtil.editPartnerDialog(requireContext(), partnersList[position])
                    .observe(viewLifecycleOwner, Observer {
                        if (it != null) {
                            partnerRecycler.visibility = View.VISIBLE
                            partnersList[position] = it
                            partnersListAdapter.data = partnersList
                            binding.partnerEmptyTxv.visibility = View.GONE
                            binding.partnerAddBtn.visibility = View.VISIBLE
                        }
                    })
            }
        })
        val viewManager = LinearLayoutManager(requireContext())
        binding.partnerRecycler.layoutManager = viewManager
        binding.partnerRecycler.adapter = partnersListAdapter


        binding.cpEditBtn.setOnClickListener {
            showEditMode()
        }

        binding.nameEmptyTxv.setOnClickListener {

            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.name)) {
                        binding.nameTxv.visibility = View.VISIBLE
                        binding.nameTxv.text = it.name
                        binding.nameEmptyTxv.visibility = View.GONE
                        binding.nameEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.nameEditBtn.setOnClickListener {
            cpFormData.name = nameTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.name)) {
                        binding.nameTxv.visibility = View.VISIBLE
                        binding.nameTxv.text = it.name
                        binding.nameEmptyTxv.visibility = View.GONE
                        binding.nameEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.phoneEmptyTxv.setOnClickListener {

            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.phone)) {
                        binding.phoneTxv.visibility = View.VISIBLE
                        binding.phoneTxv.text = it.phoneCountryId + " " + it.phone
                        binding.phoneEmptyTxv.visibility = View.GONE
                        binding.phoneNoEditBtn.visibility = View.VISIBLE
                        cpFormData.phone = it.phone
                        cpFormData.phoneCountryId = it.phoneCountryId
                    }


                })
        }

        binding.phoneNoEditBtn.setOnClickListener {
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.phone)) {
                        binding.phoneTxv.visibility = View.VISIBLE
                        binding.phoneTxv.text = it.phoneCountryId + " " + it.phone
                        binding.phoneEmptyTxv.visibility = View.GONE
                        binding.phoneNoEditBtn.visibility = View.VISIBLE
                    }


                })
        }


        binding.alternateNoEmptyTxv.setOnClickListener {

            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.alternatePhoneOne)) {
                        binding.alternateNoTxv.visibility = View.VISIBLE
                        binding.alternateNoTxv.text =
                            it.alternatePhoneOneCountryId + " " + it.alternatePhoneOne
                        binding.alternateNoEmptyTxv.visibility = View.GONE
                        binding.alternateNoEditBtn.visibility = View.VISIBLE
                        cpFormData.alternatePhoneOne = it.alternatePhoneOne
                        cpFormData.alternatePhoneOneCountryId = it.alternatePhoneOneCountryId
                    }

                })
        }
        binding.alternateNoEditBtn.setOnClickListener {

            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.alternatePhoneOne)) {
                        binding.alternateNoTxv.visibility = View.VISIBLE
                        binding.alternateNoTxv.text =
                            it.alternatePhoneOneCountryId + " " + it.alternatePhoneOne
                        binding.alternateNoEmptyTxv.visibility = View.GONE
                        binding.alternateNoEditBtn.visibility = View.VISIBLE
                        cpFormData.alternatePhoneOne = it.alternatePhoneOne
                        cpFormData.alternatePhoneOneCountryId = it.alternatePhoneOneCountryId
                    }

                })
        }

        binding.alternateNoEmptyTxv2.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.alternatePhoneTwo)) {
                        binding.alternateNoTxv2.visibility = View.VISIBLE
                        binding.alternateNoTxv2.text =
                            it.alternatePhoneTwoCountryId + " " + it.alternatePhoneTwo
                        binding.alternateNoEmptyTxv2.visibility = View.GONE
                        binding.alternateNoEditBtn2.visibility = View.VISIBLE
                        cpFormData.alternatePhoneTwo = it.alternatePhoneTwo
                        cpFormData.alternatePhoneTwoCountryId = it.alternatePhoneTwoCountryId

                    }


                })
        }
        binding.alternateNoEditBtn2.setOnClickListener {
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.alternatePhoneTwo)) {
                        binding.alternateNoTxv2.visibility = View.VISIBLE
                        binding.alternateNoTxv2.text =
                            it.alternatePhoneTwoCountryId + " " + it.alternatePhoneTwo
                        binding.alternateNoEmptyTxv2.visibility = View.GONE
                        binding.alternateNoEditBtn2.visibility = View.VISIBLE
                        cpFormData.alternatePhoneTwo = it.alternatePhoneTwo
                        cpFormData.alternatePhoneTwoCountryId = it.alternatePhoneTwoCountryId

                    }


                })
        }

        binding.addAreaChip.setOnClickListener {

            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.area)) {
                        binding.areaChipGroup.visibility = View.VISIBLE
                        val chip = Chip(requireContext())
                        chip.setChipBackgroundColorResource(R.color.light_grey)
                        chip.text = it.area
                        binding.areaChipGroup.addView(chip, 0)

                    }
                })
        }

        binding.emailEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.email)) {
                        binding.emailTxv.visibility = View.VISIBLE
                        binding.emailTxv.text = it.email
                        binding.emailEmptyTxv.visibility = View.GONE
                        binding.emailEditBtn.visibility = View.VISIBLE
                    }
                })
        }
        binding.emailEditBtn.setOnClickListener {
            cpFormData.email = binding.emailTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.email)) {
                        binding.emailTxv.visibility = View.VISIBLE
                        binding.emailTxv.text = it.email
                        binding.emailEmptyTxv.visibility = View.GONE
                        binding.emailEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.partnerEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (it.partnerList.size > 0) {
                        partnerRecycler.visibility = View.VISIBLE
                        partnersList.addAll(it.partnerList)
                        partnersListAdapter.data = partnersList
                        binding.partnerEmptyTxv.visibility = View.GONE
                        binding.partnerAddBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.partnerAddBtn.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (it.partnerList.size > 0) {
                        partnerRecycler.visibility = View.VISIBLE
                        partnersList.addAll(it.partnerList)
                        partnersListAdapter.data = partnersList
                        binding.partnerEmptyTxv.visibility = View.GONE
                        binding.partnerAddBtn.visibility = View.VISIBLE
                    }
                })
        }

        ///////firm

        binding.firmNameEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.firmName)) {
                        binding.firmNameTxv.visibility = View.VISIBLE
                        binding.firmNameTxv.text = it.firmName
                        binding.firmNameEmptyTxv.visibility = View.GONE
                        binding.firmNameEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.firmNameEditBtn.setOnClickListener {
            cpFormData.firmName = firmNameTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.firmName)) {
                        binding.firmNameTxv.text = it.firmName
                        binding.firmNameEmptyTxv.visibility = View.GONE
                    }
                })
        }
/////////

        binding.firmAddressEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.address)) {
                        binding.firmAddressTxv.visibility = View.VISIBLE
                        binding.firmAddressTxv.text = it.address
                        binding.firmAddressEmptyTxv.visibility = View.GONE
                        binding.firmAddressEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.firmAddressEditBtn.setOnClickListener {
            cpFormData.address = firmAddressTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.address)) {
                        binding.firmAddressTxv.text = it.address
                        binding.firmAddressEmptyTxv.visibility = View.GONE
                    }
                })
        }

////
        binding.firmLocalityEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.locality)) {
                        binding.firmLocalityTxv.visibility = View.VISIBLE
                        binding.firmLocalityTxv.text = it.locality
                        binding.firmLocalityEmptyTxv.visibility = View.GONE
                        binding.firmLocalityEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.firmLocalityEditBtn.setOnClickListener {
            cpFormData.locality = firmLocalityTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.locality)) {
                        binding.firmLocalityTxv.text = it.locality
                        binding.firmLocalityEmptyTxv.visibility = View.GONE
                    }
                })
        }

////
        binding.teamSizeEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.teamSize)) {
                        binding.teamSizeTxv.visibility = View.VISIBLE
                        binding.teamSizeTxv.text = it.teamSize
                        binding.teamSizeEmptyTxv.visibility = View.GONE
                        binding.teamSizeEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.teamSizeEditBtn.setOnClickListener {
            cpFormData.teamSize = teamSizeTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.teamSize)) {
                        binding.teamSizeTxv.text = it.teamSize
                        binding.teamSizeEmptyTxv.visibility = View.GONE
                    }
                })
        }


        ////////

        binding.organisationEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.organisationType)) {
                        binding.organisationTxv.visibility = View.VISIBLE
                        binding.organisationTxv.text = it.organisationType
                        binding.organisationEmptyTxv.visibility = View.GONE
                        binding.organisationEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.organisationEditBtn.setOnClickListener {
            cpFormData.organisationType = organisationTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.organisationType)) {
                        binding.organisationTxv.visibility = View.VISIBLE
                        binding.organisationTxv.text = it.organisationType
                        binding.organisationEmptyTxv.visibility = View.GONE
                        binding.organisationEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        ///////
        binding.membersEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.members)) {
                        binding.membersTxv.visibility = View.VISIBLE
                        binding.membersTxv.text = it.members
                        binding.membersEmptyTxv.visibility = View.GONE
                        binding.membersEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.membersEditBtn.setOnClickListener {
            cpFormData.members = membersTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.members)) {
                        binding.membersTxv.text = it.members
                        binding.membersEmptyTxv.visibility = View.GONE
                    }
                })
        }
/////
        binding.gstEmptyTxv.setOnClickListener {
            CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.gst)) {
                        binding.gstTxv.visibility = View.VISIBLE
                        binding.gstTxv.text = it.gst
                        binding.gstEmptyTxv.visibility = View.GONE
                        binding.gstEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.gstEditBtn.setOnClickListener {
            cpFormData.gst = gstTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.gst)) {
                        binding.gstTxv.text = it.gst
                        binding.gstEmptyTxv.visibility = View.GONE
                    }
                })
        }
/////
        binding.reraEmptyTxv.setOnClickListener {
                       CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.rera)) {
                        binding.reraTxv.visibility = View.VISIBLE
                        binding.reraTxv.text = it.rera
                        binding.reraEmptyTxv.visibility = View.GONE
                        binding.reraEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.reraEditBtn.setOnClickListener {
            cpFormData.rera = reraTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.rera)) {
                        binding.reraTxv.text = it.rera
                        binding.reraEmptyTxv.visibility = View.GONE
                    }
                })
        }
////

        binding.natureEmptyTxv.setOnClickListener {
                      CreateCpDialogUtil.openCreateDialog(it, requireContext())
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.natureOfBusiness)) {
                        binding.natureTxv.visibility = View.VISIBLE
                        binding.natureTxv.text = it.natureOfBusiness
                        binding.natureEmptyTxv.visibility = View.GONE
                        binding.natureEditBtn.visibility = View.VISIBLE
                    }
                })
        }

        binding.natureEditBtn.setOnClickListener {
            cpFormData.natureOfBusiness = natureTxv.text.toString()
            EditCpDialogeUtil.openEditDialog(it, requireContext(), cpFormData)
                .observe(viewLifecycleOwner, Observer {
                    if (CommonUtilities.notnull(it.natureOfBusiness)) {
                        binding.natureTxv.text = it.natureOfBusiness
                        binding.natureEmptyTxv.visibility = View.GONE
                    }
                })
        }


        return binding.root
    }


    private fun showAddCpView() {
        binding.nameEmptyTxv.visibility = View.VISIBLE
        binding.nameEditBtn.visibility = View.GONE

        binding.phoneEmptyTxv.visibility = View.VISIBLE
        binding.phoneNoEditBtn.visibility = View.GONE

        binding.alternateNoEmptyTxv.visibility = View.VISIBLE
        binding.alternateNoEditBtn.visibility = View.GONE

        binding.alternateNoEmptyTxv2.visibility = View.VISIBLE
        binding.alternateNoEditBtn2.visibility = View.GONE

        binding.emailEmptyTxv.visibility = View.VISIBLE
        binding.emailEditBtn.visibility = View.GONE

        binding.firmNameEmptyTxv.visibility = View.VISIBLE
        binding.firmNameEditBtn.visibility = View.GONE

        binding.firmAddressEmptyTxv.visibility = View.VISIBLE
        binding.firmAddressEditBtn.visibility = View.GONE

        binding.firmLocalityEmptyTxv.visibility = View.VISIBLE
        binding.firmLocalityEditBtn.visibility = View.GONE

        binding.teamSizeEmptyTxv.visibility = View.VISIBLE
        binding.teamSizeEditBtn.visibility = View.GONE

        binding.organisationEmptyTxv.visibility = View.VISIBLE
        binding.organisationEditBtn.visibility = View.GONE

        binding.membersEmptyTxv.visibility = View.VISIBLE
        binding.membersEditBtn.visibility = View.GONE

        binding.gstEmptyTxv.visibility = View.VISIBLE
        binding.gstEditBtn.visibility = View.GONE

        binding.reraEmptyTxv.visibility = View.VISIBLE
        binding.reraEditBtn.visibility = View.GONE

        binding.natureEmptyTxv.visibility = View.VISIBLE
        binding.natureEditBtn.visibility = View.GONE

        binding.addAreaChip.visibility = View.VISIBLE

        binding.partnerEmptyTxv.visibility = View.VISIBLE
    }

    private fun showEditMode() {

        if (CommonUtilities.notnull(binding.nameTxv.text.toString())) {
//            binding.nameTxv.text = ""
        }

        if (CommonUtilities.notnull(binding.alternateNoTxv.text.toString())) {
            binding.alternateNoEditBtn.visibility = View.VISIBLE
        }

        if (CommonUtilities.notnull(binding.alternateNoTxv2.text.toString())) {
            binding.alternateNoEditBtn2.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.emailTxv.text.toString())) {
            binding.emailEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.firmNameTxv.text.toString())) {
            binding.firmNameEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.firmAddressTxv.text.toString())) {
            binding.firmAddressEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.firmLocalityTxv.text.toString())) {
            binding.firmLocalityEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.teamSizeTxv.text.toString())) {
            binding.teamSizeEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.organisationTxv.text.toString())) {
            binding.organisationEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.membersTxv.text.toString())) {
            binding.membersEditBtn.visibility = View.VISIBLE
        }

        if (CommonUtilities.notnull(binding.gstTxv.text.toString())) {
            binding.gstEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.reraTxv.text.toString())) {
            binding.reraEditBtn.visibility = View.VISIBLE
        }
        if (CommonUtilities.notnull(binding.natureTxv.text.toString())) {
            binding.natureEditBtn.visibility = View.VISIBLE
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddCpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddCpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
