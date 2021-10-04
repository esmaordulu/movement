package com.example.movement.shared.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    val displaySpinner: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val displayAlert: MutableLiveData<Event<Int>> = MutableLiveData()
}