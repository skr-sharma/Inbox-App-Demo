package com.example.msg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.msg.R
import com.example.msg.model.InboxMsgModel
import com.example.msg.ui.adapter.viewholder.InboxMsgViewHolder

class InboxMsgAdapter(private val inboxList: List<InboxMsgModel>) :
    RecyclerView.Adapter<InboxMsgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxMsgViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_contributors, parent, false)
        return InboxMsgViewHolder(itemView)
    }

    override fun getItemCount(): Int = inboxList.size

    override fun onBindViewHolder(holder: InboxMsgViewHolder, position: Int) =
        holder.bindItems(inboxList[position])
}