package com.anarock.cpsourcing.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.adapter.CPSearchResultAdapter
import com.anarock.cpsourcing.databinding.FragmentCpSearchBinding
import com.anarock.cpsourcing.utilities.CommonUtilities.Companion.hideKeyboard
import com.anarock.cpsourcing.utilities.CommonUtilities.Companion.showKeyBoard
import com.anarock.cpsourcing.viewModel.CreateEventViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CPSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CPSearchFragment : Fragment() {
    lateinit var binding : FragmentCpSearchBinding
    val leadViewModel : CreateEventViewModel by viewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_cp_search, container, false)
        binding.search.isCursorVisible = true
        binding.search.requestFocus()
        showKeyBoard(requireContext())
        binding.close.setOnClickListener {
            hideKeyboard(requireActivity())
            findNavController().popBackStack()
        }

        val adapter = CPSearchResultAdapter()
        viewManager = LinearLayoutManager(requireContext())
        binding.leadResult.layoutManager = viewManager
        binding.leadResult.adapter = adapter

        binding.search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                leadViewModel.getLeadDetails(s.toString()).observe(viewLifecycleOwner, Observer {
                    adapter.data = it
                })
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        return binding.root
    }

}
