package com.example.msg.ui.view.inbox

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.msg.R
import com.example.msg.base.BaseFragment
import com.example.msg.databinding.FragmentInboxBinding
import com.example.msg.model.InboxMsgModel
import com.example.msg.ui.adapter.InboxMsgAdapter
import com.example.msg.ui.view.items.RecyclerSectionItemDecoration
import com.example.msg.ui.view.items.RecyclerSectionItemDecoration.SectionCallback
import kotlinx.android.synthetic.main.fragment_inbox.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InboxMsgListFragment : BaseFragment<FragmentInboxBinding, InboxMsgViewModel>(
    R.layout.fragment_inbox
) {

    override val viewModel: InboxMsgViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchLast12HoursMessages()

        viewModel.inboxMsgList.observe(viewLifecycleOwner, Observer { inboxList ->
            inboxList?.let {
                val sectionItemDecoration =
                    RecyclerSectionItemDecoration(
                        resources.getDimensionPixelSize(R.dimen.recycler_section_header_height),
                        true, getSectionCallback(it)!!
                    )
                recyclerView.addItemDecoration(sectionItemDecoration)
                recyclerView.adapter = InboxMsgAdapter(it)
            }
        })
    }

    private fun fetchLast12HoursMessages() {
        viewModel.getInboxMessages(activity)
    }

    private fun getSectionCallback(inboxMsgObjList: ArrayList<InboxMsgModel>): SectionCallback? {
        return object : SectionCallback {
            override fun isSection(position: Int): Boolean {
                return if (position > 0) {
                    val time = inboxMsgObjList[position]._time
                    val oldTime = inboxMsgObjList[position - 1]._time
                    time != oldTime
                } else
                    true
            }

            override fun getSectionHeader(position: Int): CharSequence {
                val time = inboxMsgObjList[position]._time
                return if (time?.toInt()!! > 12) {
                    "1 day ago"
                } else {
                    "$time hours ago"
                }

            }
        }
    }
}