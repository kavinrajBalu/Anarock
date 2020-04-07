package com.anarock.cpsourcing.utilities

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.model.CpFormData
import com.anarock.cpsourcing.model.PartnerFormData
import com.anarock.cpsourcing.viewModel.LoginSharedViewModel
import kotlinx.android.synthetic.main.edit_cp_dialog.*

class EditCpDialogeUtil {
    companion object {
        private val DEFAULT_MODE = "default_edit_mode"
        private val AUTOCOMPLETE_MODE = "autocomplete_edit_mode"
        private val PHONE_NUMBER_MODE = "phone_edit_mode"
        var countryCodeList = ArrayList<String>()

        fun openEditDialog(
            view: View,
            context: Context,
            value: CpFormData
        ): MutableLiveData<CpFormData> {
            var EDIT_MODE: String
            EDIT_MODE =
                if (view.id == R.id.phoneNoEditBtn || view.id == R.id.alternateNoEditBtn || view.id == R.id.alternateNoEditBtn2)
                    PHONE_NUMBER_MODE
                else if (view.id == R.id.areaEditBtn  || view.id == R.id.firmLocalityEditBtn)
                    AUTOCOMPLETE_MODE
                else
                    DEFAULT_MODE

            var cpFormMutableData: MutableLiveData<CpFormData> = MutableLiveData()
            var cpFormData = CpFormData()
            lateinit var mNameEditText: EditText
            lateinit var mPhoneEditText: EditText
            lateinit var mDoneBtn: TextView
            lateinit var spinner: Spinner
            lateinit var mLabelText: TextView
            lateinit var mCloseBtn: ImageView

//            lateinit var mAutoCompleteParent : ConstraintLayout
            lateinit var mAutoCompleteTextView: AutoCompleteTextView
            var countryCode = "+91"

            lateinit var alertDialog: AlertDialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)

            val vv: View = LayoutInflater.from(context).inflate(R.layout.edit_cp_dialog, null)
            mNameEditText = vv.findViewById<View>(R.id.editCpNameEdtxt) as EditText
            mPhoneEditText = vv.findViewById<View>(R.id.editCpNumEdtxt) as EditText
            spinner = vv.findViewById<View>(R.id.editCpSpinner) as Spinner
//                mAutoCompleteParent = vv.findViewById<View>(R.id.editCpAutocomplete) as ConstraintLayout
            mAutoCompleteTextView =
                vv.findViewById<View>(R.id.editCpAutocomplete) as AutoCompleteTextView
            val adapter =
                ArrayAdapter(
                    context, android.R.layout.simple_spinner_item,
                    countryCodeList
                )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            var countryId = 49

            mLabelText = vv.findViewById<View>(R.id.edit_dialog_title) as TextView
            mCloseBtn = vv.findViewById<View>(R.id.closeEditDialog) as ImageView
            mDoneBtn = vv.findViewById<View>(R.id.editDoneBtn) as TextView


            when (EDIT_MODE) {
                DEFAULT_MODE -> {
                    mNameEditText.visibility = View.VISIBLE
                    mNameEditText.setText(value.name)
                }
                PHONE_NUMBER_MODE -> {
                    spinner.visibility = View.VISIBLE
                    mPhoneEditText.visibility = View.VISIBLE
                    mLabelText.text = "Edit Phone number"
                }

                AUTOCOMPLETE_MODE -> {
                    mAutoCompleteTextView.visibility = View.VISIBLE

                }
            }

            if (view.id == R.id.emailEditBtn) {
                mLabelText.text = "Edit email"
                mNameEditText.hint = "Enter email"
                mNameEditText.setText(value.email)
            } else if (view.id == R.id.phoneNoEditBtn) {
                mPhoneEditText.setText(value.phone)
                spinner.setSelection(countryCodeList.indexOf(value.phoneCountryId))
            } else if (view.id == R.id.alternateNoEditBtn) {
                mPhoneEditText.setText(value.alternatePhoneOne)
                spinner.setSelection(countryCodeList.indexOf(value.alternatePhoneOneCountryId))
            } else if (view.id == R.id.alternateNoEditBtn2) {
                mPhoneEditText.setText(value.alternatePhoneTwo)
                spinner.setSelection(countryCodeList.indexOf(value.alternatePhoneTwoCountryId))
            }else if (view.id == R.id.firmNameEditBtn) {
                mLabelText.text = "Edit firm name"
                mNameEditText.hint = "Enter firm name"
                mNameEditText.setText(value.firmName)
            }else if (view.id == R.id.firmAddressEditBtn) {
                mLabelText.text = "Edit address"
                mNameEditText.hint = "Enter address"
                mNameEditText.setText(value.address)
            }else if (view.id == R.id.firmLocalityEditBtn) {
                mLabelText.text = "Edit locality"
                mAutoCompleteTextView.hint = "Firm locality"
                mAutoCompleteTextView.setText(value.locality)
            }else if (view.id == R.id.teamSizeEditBtn) {
                mLabelText.text = "Edit team size"
                mNameEditText.hint = "Enter team size"
                mNameEditText.setText(value.teamSize)
            }else if (view.id == R.id.organisationEditBtn) {
                mLabelText.text = "Edit organisation type"
                mNameEditText.hint = "Enter type"
                mNameEditText.setText(value.organisationType)
            }else if (view.id == R.id.membersEditBtn) {
                mLabelText.text = "Edit members of associations"
                mNameEditText.hint = "Enter members"
                mNameEditText.setText(value.members)
            }else if (view.id == R.id.gstEditBtn) {
                mLabelText.text = "Edit GST number"
                mNameEditText.hint = "Enter GST number"
                mNameEditText.setText(value.gst)
            }else if (view.id == R.id.reraEditBtn) {
                mLabelText.text = "Edit RERA number"
                mNameEditText.hint = "Enter RERA number"
                mNameEditText.setText(value.rera)
            }else if (view.id == R.id.natureEditBtn) {
                mLabelText.text = "Edit business nature"
                mNameEditText.hint = "Enter nature of business"
                mNameEditText.setText(value.natureOfBusiness)
            }


            mDoneBtn.setOnClickListener {
                if (view.id == R.id.nameEditBtn) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.name = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter name"
                } else if (view.id == R.id.phoneNoEditBtn) {
                    if (CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())) {
                        cpFormData.phone = mPhoneEditText.text.toString()
                        cpFormData.phoneCountryId = countryCode
                        alertDialog.dismiss()

                    } else {
                        mPhoneEditText.error = "Enter a valid phone number"
                    }

                } else if (view.id == R.id.alternateNoEditBtn) {
                    if(CommonUtilities.notnull(mPhoneEditText.text.toString()) && !CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())){
                        mPhoneEditText.error = "Enter a valid phone number"
                    }else{
                        cpFormData.alternatePhoneOne = mPhoneEditText.text.toString()
                        cpFormData.alternatePhoneOneCountryId = countryCode
                        alertDialog.dismiss()

                    }
                    if (CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())) {

                        cpFormData.alternatePhoneOne = mPhoneEditText.text.toString()
                        cpFormData.alternatePhoneOneCountryId = countryCode
                        alertDialog.dismiss()

                    } else {
                        mPhoneEditText.error = "Enter a valid phone number"
                    }
                } else if (view.id == R.id.alternateNoEditBtn2) {
                    if(CommonUtilities.notnull(mPhoneEditText.text.toString()) && !CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())){
                        mPhoneEditText.error = "Enter a valid phone number"
                    }else{
                        cpFormData.alternatePhoneTwo = mPhoneEditText.text.toString()
                        cpFormData.alternatePhoneTwoCountryId = countryCode
                        alertDialog.dismiss()
                    }

                } else if (view.id == R.id.emailEditBtn) {
                    if(CommonUtilities.notnull(mNameEditText.text.toString()) && !CommonUtilities.isValidEmail(mNameEditText.text.toString())){
                        mNameEditText.error = "Enter valid email"
                    }else{
                        cpFormData.email = mNameEditText.text.toString()
                        alertDialog.dismiss()
                    }
                  /*  if (CommonUtilities.isValidEmail(mNameEditText.text.toString())) {
                        cpFormData.email = mNameEditText.text.toString()
                        alertDialog.dismiss()
                    } else
                        mNameEditText.error = "Enter valid email"
*/
                } else if (view.id == R.id.firmNameEditBtn) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.firmName = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter firm name"
                } else if (view.id == R.id.firmAddressEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.address = mNameEditText.text.toString()
                        alertDialog.dismiss()

                  /*  } else
                        mNameEditText.error = "Enter address"*/
                } else if (view.id == R.id.firmLocalityEditBtn) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.locality = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter locality"
                } else if (view.id == R.id.teamSizeEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.teamSize = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    /*} else
                        mNameEditText.error = "Enter team size"*/
                } else if (view.id == R.id.organisationEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.organisationType = mNameEditText.text.toString()
                        alertDialog.dismiss()

                   /* } else
                        mNameEditText.error = "Enter organisation type"*/
                } else if (view.id == R.id.membersEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.members = mNameEditText.text.toString()
                        alertDialog.dismiss()

                   /* } else
                        mNameEditText.error = "Enter members"*/
                } else if (view.id == R.id.gstEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.gst = mNameEditText.text.toString()
                        alertDialog.dismiss()

                   /* } else
                        mNameEditText.error = "Enter gst number"*/
                } else if (view.id == R.id.reraEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.rera = mNameEditText.text.toString()
                        alertDialog.dismiss()

                   /* } else
                        mNameEditText.error = "Enter rera number"*/
                } else if (view.id == R.id.natureEditBtn) {
//                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.natureOfBusiness = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    /*} else
                        mNameEditText.error = "Enter nature of business"*/
                } else {
                }
                cpFormMutableData.value = cpFormData
            }

            mCloseBtn.setOnClickListener {
                alertDialog.dismiss()
            }

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?, position: Int, id: Long
                ) {
                    countryCode = countryCodeList[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }

            mAutoCompleteTextView.addTextChangedListener {

            }
            builder.setView(vv)
            builder.setCancelable(true)
            alertDialog = builder.create()
            alertDialog.show()

            return cpFormMutableData
        }

        fun editPartnerDialog(
            context: Context,
            partnerFormData: PartnerFormData
        ): MutableLiveData<PartnerFormData> {

            val partnerFormMutableLiveData: MutableLiveData<PartnerFormData> = MutableLiveData()
            lateinit var mPartnerName: EditText
            lateinit var mPartnerMobile: EditText
            lateinit var mPartnerDesignation: EditText
            lateinit var mPartnerEmail: EditText

            lateinit var alertDialog: AlertDialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val vv: View = LayoutInflater.from(context).inflate(R.layout.edit_partner_dialog, null)

            val nameParent = vv.findViewById<View>(R.id.partnerNamEdtxt) as ConstraintLayout
            mPartnerName = nameParent.findViewById<View>(R.id.editText) as EditText

            val mobileParent = vv.findViewById<View>(R.id.partnerPhnoEdtxt) as ConstraintLayout
            mPartnerMobile = mobileParent.findViewById<View>(R.id.editText) as EditText

            val desgtnParent = vv.findViewById<View>(R.id.partnerDesgnEdtxt) as ConstraintLayout
            mPartnerDesignation = desgtnParent.findViewById<View>(R.id.editText) as EditText

            val emailParent = vv.findViewById<View>(R.id.partnerEmailEdtxt) as ConstraintLayout
            mPartnerEmail = emailParent.findViewById<View>(R.id.editText) as EditText
            val mCloseBtn = vv.findViewById<View>(R.id.closeEditDialog) as ImageView
            val mDoneBtn = vv.findViewById<View>(R.id.editDoneBtn) as TextView


            if (partnerFormData != null) {
                mPartnerName.setText(partnerFormData.name)
                mPartnerMobile.setText(partnerFormData.phone)
                mPartnerDesignation.setText(partnerFormData.designation)
                mPartnerEmail.setText(partnerFormData.email)
            }

            mDoneBtn.setOnClickListener {
                val partnerEditedFormData = PartnerFormData()

                var validated = true
                if (!CommonUtilities.notnull(mPartnerName.text.toString())) {
                    mPartnerName.error = "Enter name"
                    validated = false
                }
                if (!CommonUtilities.isValidPhoneNumber(mPartnerMobile.text.toString())) {
                    mPartnerMobile.error = "Enter a valid number"
                    validated = false
                }
                if (!CommonUtilities.notnull(mPartnerDesignation.text.toString())) {
                    mPartnerDesignation.error = "Enter designation"
                    validated = false
                }
                if (!CommonUtilities.isValidEmail(mPartnerEmail.text.toString())) {
                    mPartnerEmail.error = "Enter valid email"
                    validated = false
                }
                if (validated) {
                    partnerEditedFormData.name = mPartnerName.text.toString()
                    partnerEditedFormData.phone = mPartnerMobile.text.toString()
                    partnerEditedFormData.designation = mPartnerDesignation.text.toString()
                    partnerEditedFormData.email = mPartnerEmail.text.toString()
                    partnerEditedFormData.editable = true
                    partnerFormMutableLiveData.value = partnerEditedFormData

                    alertDialog.dismiss()
                }


            }

            mCloseBtn.setOnClickListener {
                alertDialog.dismiss()
            }

            builder.setView(vv)
            builder.setCancelable(true)
            alertDialog = builder.create()
            alertDialog.show()

            return partnerFormMutableLiveData


        }

        fun setCountryCodeArray(countryCodes: ArrayList<String>) {
            countryCodeList = countryCodes
        }
    }

}