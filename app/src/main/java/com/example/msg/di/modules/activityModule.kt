package com.example.msg.di.modules

import com.example.msg.ui.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {
    viewModel {
        MainViewModel()
    }
}