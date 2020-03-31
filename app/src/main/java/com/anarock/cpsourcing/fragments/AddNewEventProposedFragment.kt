package com.anarock.cpsourcing.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.databinding.FragmentAddNewEventProposedBinding
import com.anarock.cpsourcing.model.CPSearchPayload
import com.anarock.cpsourcing.model.CustomAppBar
import com.anarock.cpsourcing.model.EventCreationPayload
import com.anarock.cpsourcing.utilities.DateTimeUtils
import com.anarock.cpsourcing.viewModel.CreateEventViewModel
import com.anarock.cpsourcing.viewModel.SharedUtilityViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.custom_spinner.view.*
import kotlinx.android.synthetic.main.custom_text_field.view.*
import java.util.*
import kotlin.collections.ArrayList


class AddNewEventProposedFragment : Fragment() {

    private val sharedUtilityViewModel : SharedUtilityViewModel by activityViewModels()
    private val createEventViewModel: CreateEventViewModel by viewModels()
    private lateinit var binding : FragmentAddNewEventProposedBinding
    private lateinit var projectSpinner : Spinner
    private lateinit var datePickerDialog: DatePickerDialog
    private val DATE_FORMAT = "EE, MMM dd - hh:ssaa"
    val startTime = Calendar.getInstance()
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_new_event_proposed, container, false)
            sharedUtilityViewModel.setToolBarVisibility(true)
            sharedUtilityViewModel.setCustomToolBar(CustomAppBar(android.R.color.white,
                R.color.anarock_blue
            ))
            sharedUtilityViewModel.setCustomStatusBar(android.R.color.white)
            val items = arrayOf("NMas", "NaasY", "NasaC", "NasaD")
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
            binding.projectSpinner.spinner.setAdapter(adapter)
            binding.cpSpinner.field.spinner.setAdapter(adapter)
            initViews()

            binding.dateTime.customTextInput.editText?.setOnClickListener {
                datePickerDialog.show()
            }

            val time = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                     startTime[Calendar.HOUR_OF_DAY] = hourOfDay
                     startTime[Calendar.MINUTE] = minute
                binding.dateTime.customTextInput.editText?.setText(DateTimeUtils.customDateTimeString(DATE_FORMAT,startTime))
            }

            val timePickerDialog  = TimePickerDialog(requireContext(),
                R.style.DialogTheme,time,Calendar.HOUR_OF_DAY,Calendar.MINUTE,false)
            val date =
                OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    startTime[Calendar.YEAR] = year
                    startTime[Calendar.MONTH] = monthOfYear
                    startTime[Calendar.DAY_OF_MONTH] = dayOfMonth
                    timePickerDialog.show()
                    //updateLabel()
                }

            datePickerDialog = DatePickerDialog(
                requireContext(),
                R.style.DialogTheme,date, startTime
                    .get(Calendar.YEAR), startTime.get(Calendar.MONTH),
                startTime.get(Calendar.DAY_OF_MONTH)
            )


            binding.reminderChipGroup.setOnCheckedChangeListener { chipGroup, id ->
                val chip = chipGroup.findViewById<Chip>(id)
            }

            binding.eventType.setOnClickListener {
                findNavController().navigate(R.id.action_addNewEventProposedFragment_to_addNewEvent)
            }

            binding.clear.setOnClickListener {
                   clearAllFields()
            }

            binding.addEvent.setOnClickListener {
                if(isMandatoryFieldFilled())
                {
                    createEventViewModel.eventCreateAPI(getPayloadObject()).observe(viewLifecycleOwner,
                        androidx.lifecycle.Observer {

                            findNavController().navigate(R.id.action_addNewEventProposedFragment_to_eventFragement)

                        })
                }
            }

            binding.cpSpinner.field.spinner.addTextChangedListener(object :TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                     createEventViewModel.searchCP(CPSearchPayload(s.toString())).observe(viewLifecycleOwner,
                         androidx.lifecycle.Observer {
                             val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,ArrayList(it.values))
                             binding.projectSpinner.spinner.setAdapter(adapter)
                             binding.cpSpinner.field.spinner.setAdapter(adapter)
                         })
                }
            })
            return binding.root
    }

    private fun getPayloadObject(): EventCreationPayload {
           return  EventCreationPayload(1,123,startTime.time.toString(),"",12,23,binding.notes.text.toString())
    }

    private fun isMandatoryFieldFilled(): Boolean {
        var isSuccess = true
        if(binding.projectSpinner.field.spinner.text.isEmpty())
        {
            binding.projectSpinner.field.error = "Project required"
            isSuccess= false
        }

        if(binding.cpSpinner.field.spinner.text.isEmpty())
        {
            binding.cpSpinner.field.error = "CP required"
            isSuccess= false
        }

        if(binding.leadName.field.spinner.text.isEmpty())
        {
            binding.leadName.field.error = "Lead name required"
            isSuccess= false
        }

        if(binding.leadPhoneNumber.customTextInput.editText?.text?.isEmpty() == true)
        {
            binding.leadPhoneNumber.customTextInput.error = "Phone number required"
            isSuccess= false
        }

        if(binding.dateTime.customTextInput.editText?.text?.isEmpty() == true)
        {
            binding.dateTime.customTextInput.error = "Date time required"
            isSuccess= false
        }
     return isSuccess
    }

    private fun clearAllFields() {
        binding.projectSpinner.field.spinner.text.clear()
        binding.cpSpinner.field.spinner.text.clear()
        binding.leadName.field.spinner.text?.clear()
        binding.leadPhoneNumber.customTextInput.editText?.text?.clear()
        binding.dateTime.customTextInput.editText?.text?.clear()
        binding.reminderChipGroup.clearCheck()
        binding.notes.text.clear()
    }

    private fun initViews() {
        binding.projectSpinner.field.spinner.hint = getString(R.string.select_project)
        binding.cpSpinner.field.spinner.hint = getString(R.string.search_cp)
        binding.leadName.field.spinner.hint = getString(R.string.search_lead_name)
        binding.leadPhoneNumber.customTextInput.editText?.inputType = InputType.TYPE_CLASS_PHONE
        binding.leadPhoneNumber.customTextInput.editText?.hint = getString(R.string.lead_phone_number)
        binding.dateTime.customTextInput.editText?.hint = getString(R.string.date_time_hint)
    }
}
