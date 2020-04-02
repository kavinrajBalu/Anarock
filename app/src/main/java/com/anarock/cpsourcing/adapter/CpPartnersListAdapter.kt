package com.anarock.cpsourcing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.interfaces.onClickEventListener
import com.anarock.cpsourcing.model.PartnerFormData

class CpPartnersListAdapter(var clickEventListener: onClickEventListener) : RecyclerView.Adapter<CpPartnersListAdapter.ViewHolder>() {
    var data = listOf<PartnerFormData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var mClickEventListener = clickEventListener

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener {

        private val partnerName: TextView = itemView.findViewById(R.id.partnerNameTxv)
        private val mobileNumber: TextView = itemView.findViewById(R.id.partnerPhNoTxv)
        private val designation: TextView = itemView.findViewById(R.id.designationTxv)
        private val email: TextView = itemView.findViewById(R.id.partnerEmailTxv)
        private val editBtn: ImageView = itemView.findViewById(R.id.partnerEditBtn)

        fun bind(item: PartnerFormData) {
            partnerName.text = item.name
            mobileNumber.text = item.phone.toString()
            designation.text = item.designation
            email.text = item.email

            editBtn.visibility = if(item.editable) View.VISIBLE else View.GONE

            editBtn.setOnClickListener(this)

        }

       /* companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.partners_list_item, parent, false)
                return ViewHolder(view)
            }
        }*/

        override fun onClick(p0: View?) {
            if (p0 != null) {
                if (p0.id == editBtn.id) {
                    editPartner(data[adapterPosition], adapterPosition)
                }
            }
        }

    }

    fun editPartner(partnerFormData: PartnerFormData, adapterPosition: Int){
        mClickEventListener.clickEvent(1, partnerFormData, adapterPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.partners_list_item, parent, false))    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

}