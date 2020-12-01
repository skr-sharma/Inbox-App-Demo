package com.example.msg.ui.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.msg.model.InboxMsgModel
import kotlinx.android.synthetic.main.item_contributors.view.*

class InboxMsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItems(inboxMsgModel: InboxMsgModel) {
        itemView.msgTitle.text = inboxMsgModel._address
        itemView.msgText.text = inboxMsgModel._msg
    }
}