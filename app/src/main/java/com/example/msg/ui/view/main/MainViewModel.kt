package com.example.msg.ui.view.main

import androidx.lifecycle.MutableLiveData
import com.example.msg.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    private var onClickButton = MutableLiveData<Unit>()

    fun openFragment() {
        onClickButton.value = Unit
    }
}