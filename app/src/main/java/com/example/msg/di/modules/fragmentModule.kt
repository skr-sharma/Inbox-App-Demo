package com.example.msg.di.modules

import com.example.msg.ui.view.inbox.InboxMsgViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val fragmentModule = module {
    viewModel {
        InboxMsgViewModel()
    }
}