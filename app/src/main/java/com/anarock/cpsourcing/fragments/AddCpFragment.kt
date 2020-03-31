package com.anarock.cpsourcing.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentAddCpBinding
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.utilities.CommonUtilities
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel


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
    private val DEFAULT_MODE = "default_edit_mode"
    private val AUTOCOMPLETE_MODE = "autocomplete_edit_mode"
    private val PHONE_NUMBER_MODE = "phone_edit_mode"
    private val PARTNER_MODE = "partner_edit_mode"



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

        sharedUtilityViewModel.setCustomToolBar(
            CustomAppBar(
                R.color.anarock_blue,
                android.R.color.white
            )
        )

        binding.cpEditBtn.setOnClickListener {
            showEditMode()
        }

        return binding.root
    }

    fun openEditDialog(view: View) {
        var EDIT_MODE: String
        EDIT_MODE = if (view.id == R.id.phoneEmptyTxv)
            PHONE_NUMBER_MODE
        else if (view.id == R.id.areaEmptyTxv)
            AUTOCOMPLETE_MODE
        else if (view.id == R.id.partnerEmptyTxv)
            PARTNER_MODE
        else
            DEFAULT_MODE


        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val vv: View = LayoutInflater.from(requireContext()).inflate(R.layout.edit_cp_dialog, null)
        val mNameEditText = vv.findViewById<View>(R.id.editCpNameEdtxt) as EditText

        when (EDIT_MODE) {
            DEFAULT_MODE -> {
                mNameEditText.visibility = View.VISIBLE
            }
            DEFAULT_MODE -> {
                mNameEditText.visibility = View.VISIBLE
            }

            DEFAULT_MODE -> {
                mNameEditText.visibility = View.VISIBLE
            }

            DEFAULT_MODE -> {
                mNameEditText.visibility = View.VISIBLE
            }
        }

        builder.setView(vv)
        builder.setCancelable(true)
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

        binding.areaEmptyTxv.visibility = View.VISIBLE

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
