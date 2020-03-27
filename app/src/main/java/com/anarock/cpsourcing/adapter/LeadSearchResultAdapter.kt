package com.anarock.cpsourcing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.model.LeadSearchData

class LeadSearchResultAdapter : RecyclerView.Adapter<LeadSearchResultAdapter.ViewHolder>()
{
    var data =  listOf<LeadSearchData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder private constructor(itemView : View):RecyclerView.ViewHolder(itemView){

        private val leadName: TextView = itemView.findViewById(R.id.lead_name)
        private val mobileNumber: TextView = itemView.findViewById(R.id.mobile_number)

        fun bind(item : LeadSearchData)
        {
            leadName.text = item.leadName
            mobileNumber.text = item.mobile
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.lead_search_item, parent, false)
                return ViewHolder(view)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}