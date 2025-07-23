package com.cacique.caciqueci.ui.additional

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SupermarketViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is supermarket Fragment"
    }
    val text: LiveData<String> = _text
}