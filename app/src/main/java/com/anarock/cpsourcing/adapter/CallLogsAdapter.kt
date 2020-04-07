package com.anarock.cpsourcing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anarock.cpsourcing.R
import com.anarock.cpsourcing.callLogs.CallLogsItem
import com.anarock.cpsourcing.callLogs.CallLogsListItemType
import com.anarock.cpsourcing.callLogs.DateItem
import com.anarock.cpsourcing.utilities.DateTimeUtils
import java.lang.Exception

class CallLogsAdapter(private val listItem: ArrayList<CallLogsListItemType>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            0 -> {
                val view = inflater.inflate(R.layout.call_logs_header, parent, false)
                viewHolder = HeaderViewHolder(view)
            }
            1 -> {
                val view = inflater.inflate(R.layout.call_logs_item, parent, false)
                viewHolder = DataViewHolder(view)
            }
        }

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun getItemViewType(position: Int): Int {
        return listItem[position].getType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                val dateItem = listItem[position] as DateItem
                val headerViewHolder = holder as HeaderViewHolder
                headerViewHolder.bind(dateItem)

            }
            1 -> {
                val callLogsItem = listItem[position] as CallLogsItem
                val callLogsDataHolder = holder as DataViewHolder
                var isHideFooter = false
                try{
                    val error = listItem[position+1] as CallLogsItem
                }
                catch (e:Exception)
                {
                    isHideFooter = true
                }
                callLogsDataHolder.bind(callLogsItem,isHideFooter)

            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date = itemView.findViewById<TextView>(R.id.date)

        fun bind(dateItem: DateItem)
        {
            if(DateTimeUtils.isToday(dateItem.getDateTime()))
            {
                date.text = "TODAY"
            }
            else
            {
                date.text = dateItem.getDate()    
            }
            
        }

    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val phoneNumber = itemView.findViewById<TextView>(R.id.phone_number)
        private val footer = itemView.findViewById<View>(R.id.divider)

        fun bind(
            callLogsItem: CallLogsItem,
            hideFooter: Boolean
        )
        {
            phoneNumber.text = callLogsItem.getCallLogs().dateTime
            footer.visibility = if(hideFooter) View.GONE else View.VISIBLE
        }
    }
}