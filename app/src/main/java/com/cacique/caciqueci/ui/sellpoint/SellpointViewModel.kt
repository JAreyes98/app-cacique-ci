package com.cacique.caciqueci.ui.sellpoint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SellpointViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is sellpoint Fragment"
    }
    val text: LiveData<String> = _text
}