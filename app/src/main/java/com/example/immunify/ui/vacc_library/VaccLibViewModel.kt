package com.example.immunify.ui.vacc_library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VaccLibViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Vaccination library Fragment"
    }
    val text: LiveData<String> = _text
}