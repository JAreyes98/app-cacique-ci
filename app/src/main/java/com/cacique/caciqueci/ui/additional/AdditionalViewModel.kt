package com.cacique.caciqueci.ui.additional

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdditionalViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is additional Fragment"
    }
    val text: LiveData<String> = _text
}