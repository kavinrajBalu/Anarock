package com.anarock.cpsourcing.utilities

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.interfaces.placesPredictListener
import com.anarock.cpsourcing.model.CpFormData
import com.anarock.cpsourcing.model.PartnerFormData
import com.anarock.cpsourcing.model.PlacesPredict
import com.google.android.material.chip.Chip


class CreateCpDialogUtil {
    companion object {
        private val DEFAULT_MODE = "default_edit_mode"
        private val AUTOCOMPLETE_MODE = "autocomplete_edit_mode"
        private val PHONE_NUMBER_MODE = "phone_edit_mode"
        private val PARTNER_MODE = "partner_edit_mode"
        var countryCodeList = ArrayList<String>()

        fun openCreateDialog(view: View, context: Context): MutableLiveData<CpFormData> {
            var EDIT_MODE: String =
                if (view.id == R.id.phoneEmptyTxv || view.id == R.id.alternateNoEmptyTxv || view.id == R.id.alternateNoEmptyTxv2)
                    PHONE_NUMBER_MODE
                else if (view is Chip || view.id == R.id.firmLocalityEmptyTxv)
                    AUTOCOMPLETE_MODE
                else if (view.id == R.id.partnerEmptyTxv || view.id == R.id.partnerAddBtn)
                    PARTNER_MODE
                else
                    DEFAULT_MODE

            /* if (view is Chip) {
                 val imageView = view
                 // do what you want with imageView
             }*/

            var cpFormMutableData: MutableLiveData<CpFormData> = MutableLiveData()
            var cpFormData = CpFormData()
            lateinit var mNameEditText: EditText
            lateinit var mPhoneEditText: EditText
            lateinit var mDoneBtn: TextView
            lateinit var spinner: Spinner
            lateinit var mLabelText: TextView
            lateinit var mCloseBtn: ImageView

            lateinit var mPartnerName: EditText
            lateinit var mPartnerMobile: EditText
            lateinit var mPartnerDesignation: EditText
            lateinit var mPartnerEmail: EditText

//            lateinit var mAutoCompleteParent : ConstraintLayout
            lateinit var mAutoCompleteTextView: AutoCompleteTextView
            var countryCode = "+91"

            lateinit var alertDialog: AlertDialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            val vv: View
            if (EDIT_MODE == PARTNER_MODE) {
                vv = LayoutInflater.from(context).inflate(R.layout.edit_partner_dialog, null)
                val nameParent = vv.findViewById<View>(R.id.partnerNamEdtxt) as ConstraintLayout
                mPartnerName = nameParent.findViewById<View>(R.id.editText) as EditText

                val mobileParent = vv.findViewById<View>(R.id.partnerPhnoEdtxt) as ConstraintLayout
                mPartnerMobile = mobileParent.findViewById<View>(R.id.editText) as EditText

                val desgtnParent = vv.findViewById<View>(R.id.partnerDesgnEdtxt) as ConstraintLayout
                mPartnerDesignation = desgtnParent.findViewById<View>(R.id.editText) as EditText

                val emailParent = vv.findViewById<View>(R.id.partnerEmailEdtxt) as ConstraintLayout
                mPartnerEmail = emailParent.findViewById<View>(R.id.editText) as EditText


            } else {
                vv = LayoutInflater.from(context).inflate(R.layout.edit_cp_dialog, null)
                mNameEditText = vv.findViewById<View>(R.id.editCpNameEdtxt) as EditText
                mPhoneEditText = vv.findViewById<View>(R.id.editCpNumEdtxt) as EditText
                spinner = vv.findViewById<View>(R.id.editCpSpinner) as Spinner
//                mAutoCompleteParent = vv.findViewById<View>(R.id.editCpAutocomplete) as ConstraintLayout
                mAutoCompleteTextView =
                    vv.findViewById<View>(R.id.editCpAutocomplete) as AutoCompleteTextView
                val adapter =
                    ArrayAdapter(context, android.R.layout.simple_spinner_item, countryCodeList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
                var countryId = 49

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

            }
            mLabelText = vv.findViewById<View>(R.id.edit_dialog_title) as TextView
            mCloseBtn = vv.findViewById<View>(R.id.closeEditDialog) as ImageView
            mDoneBtn = vv.findViewById<View>(R.id.editDoneBtn) as TextView

            mDoneBtn.text = "Add"


            when (EDIT_MODE) {
                DEFAULT_MODE -> {
                    mNameEditText.visibility = View.VISIBLE
                }
                PHONE_NUMBER_MODE -> {
                    spinner.visibility = View.VISIBLE
                    mPhoneEditText.visibility = View.VISIBLE
                    mLabelText.text = "Add Phone number"
                }

                AUTOCOMPLETE_MODE -> {
                    mAutoCompleteTextView.visibility = View.VISIBLE

                }
            }

            if (view.id == R.id.emailEmptyTxv) {
                mLabelText.text = "Add email"
                mNameEditText.visibility = View.VISIBLE
                mNameEditText.hint = "Enter email"
            } else if (view.id == R.id.addAreaChip) {
                mLabelText.text = "Area of Operation"
                mAutoCompleteTextView.hint = "Add area name"
            } else if (view.id == R.id.firmNameEmptyTxv) {
                mLabelText.text = "Add firm name"
                mNameEditText.hint = "Enter firm name"
            } else if (view.id == R.id.firmAddressEmptyTxv) {
                mLabelText.text = "Add address"
                mNameEditText.hint = "Enter address"
            } else if (view.id == R.id.firmLocalityEmptyTxv) {
                mLabelText.text = "Add locality"
                mAutoCompleteTextView.hint = "Firm locality"
            } else if (view.id == R.id.teamSizeEmptyTxv) {
                mLabelText.text = "Add team size"
                mNameEditText.hint = "Enter team size"
            } else if (view.id == R.id.organisationEmptyTxv) {
                mLabelText.text = "Add organisation type"
                mNameEditText.hint = "Enter type"
            } else if (view.id == R.id.membersEmptyTxv) {
                mLabelText.text = "Add members of associations"
                mNameEditText.hint = "Enter members"
            } else if (view.id == R.id.gstEmptyTxv) {
                mLabelText.text = "Add GST number"
                mNameEditText.hint = "Enter GST number"
            } else if (view.id == R.id.reraEmptyTxv) {
                mLabelText.text = "Add RERA number"
                mNameEditText.hint = "Enter RERA number"
            } else if (view.id == R.id.natureEmptyTxv) {
                mLabelText.text = "Add business nature"
                mNameEditText.hint = "Enter nature of business"
            }
            var fruits = listOf("apple", "grapes", "jhjguy")

            var placesList = ArrayList<String>()
            var adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(context, android.R.layout.select_dialog_item, fruits)

            mAutoCompleteTextView.setAdapter(adapter)
            mAutoCompleteTextView.threshold = 3
            mAutoCompleteTextView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as String
                mAutoCompleteTextView.apply {
                    setText(place)
                    setSelection(mAutoCompleteTextView.length())
                }
            }
            mAutoCompleteTextView.addTextChangedListener {
//                if (it.toString().length > 2) {
                    PlacesAutoCompleteUtil.findAutoCompletePredictions(
                        it.toString(),
                        context,
                        object : placesPredictListener {
                            override fun onResponse(placesPredict: PlacesPredict) {

//                                placesPredict.placePrimary?.let { it1 -> placesList.add(it1) }
//                                adapter.notifyDataSetChanged()

                            }

                        })

//                }
            }


            mDoneBtn.setOnClickListener {
                if (view.id == R.id.nameEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.name = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter name"
                } else if (view.id == R.id.phoneEmptyTxv) {
                    if (CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())) {
                        cpFormData.phone = mPhoneEditText.text.toString()
                        cpFormData.phoneCountryId = countryCode
                        alertDialog.dismiss()

                    } else {
                        mPhoneEditText.error = "Enter a valid phone number"
                    }

                } else if (view.id == R.id.alternateNoEmptyTxv) {
                    if (CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())) {

                        cpFormData.alternatePhoneOne = mPhoneEditText.text.toString()
                        cpFormData.alternatePhoneOneCountryId = countryCode
                        alertDialog.dismiss()

                    } else {
                        mPhoneEditText.error = "Enter a valid phone number"
                    }

                } else if (view.id == R.id.alternateNoEmptyTxv2) {
                    if (CommonUtilities.isValidPhoneNumber(mPhoneEditText.text.toString())) {
                        cpFormData.alternatePhoneTwo = mPhoneEditText.text.toString()
                        cpFormData.alternatePhoneTwoCountryId = countryCode
                        alertDialog.dismiss()

                    } else {
                        mPhoneEditText.error = "Enter a valid phone number"
                    }

                } else if (view.id == R.id.emailEmptyTxv) {
                    if (CommonUtilities.isValidEmail(mNameEditText.text.toString())) {
                        cpFormData.email = mNameEditText.text.toString()
                        alertDialog.dismiss()
                    } else
                        mNameEditText.error = "Enter valid email"

                } else if (view.id == R.id.addAreaChip) {
                    if (CommonUtilities.notnull(mAutoCompleteTextView.text.toString())) {
                        cpFormData.area = mAutoCompleteTextView.text.toString()
                        alertDialog.dismiss()
                    } else
                        mNameEditText.error = "Pick a valid area"

                } else if (view.id == R.id.firmNameEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.firmName = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter firm name"
                } else if (view.id == R.id.firmAddressEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.address = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter address"
                } else if (view.id == R.id.firmLocalityEmptyTxv) {
                    if (CommonUtilities.notnull(mAutoCompleteTextView.text.toString())) {
                        cpFormData.locality = mAutoCompleteTextView.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter locality"
                } else if (view.id == R.id.teamSizeEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.teamSize = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter team size"
                } else if (view.id == R.id.organisationEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.organisationType = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter organisation type"
                } else if (view.id == R.id.membersEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.members = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter members"
                } else if (view.id == R.id.gstEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.gst = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter gst number"
                } else if (view.id == R.id.reraEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.rera = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter rera number"
                } else if (view.id == R.id.natureEmptyTxv) {
                    if (CommonUtilities.notnull(mNameEditText.text.toString())) {
                        cpFormData.natureOfBusiness = mNameEditText.text.toString()
                        alertDialog.dismiss()

                    } else
                        mNameEditText.error = "Enter nature of business"
                } else if (view.id == R.id.partnerEmptyTxv || view.id == R.id.partnerAddBtn) {
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
                        val partnerList = ArrayList<PartnerFormData>()
                        val partnerFormData = PartnerFormData()
                        partnerFormData.name = mPartnerName.text.toString()
                        partnerFormData.phone = mPartnerMobile.text.toString()
                        partnerFormData.designation = mPartnerDesignation.text.toString()
                        partnerFormData.email = mPartnerEmail.text.toString()
                        partnerFormData.editable = true
                        partnerList.add(partnerFormData)
                        cpFormData.partnerList = partnerList
                        alertDialog.dismiss()
                    }

                } else {
                }
                cpFormMutableData.value = cpFormData
            }

            mCloseBtn.setOnClickListener {
                alertDialog.dismiss()
            }



            builder.setView(vv)
            builder.setCancelable(true)
            alertDialog = builder.create()
            alertDialog.show()

            return cpFormMutableData
        }

        fun setCountryCodeArray(countryCodes: ArrayList<String>) {
            countryCodeList = countryCodes
        }
    }

}