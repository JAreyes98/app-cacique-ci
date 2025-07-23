package com.cacique.caciqueci.ui.additional

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cacique.caciqueci.databinding.FragmentAdditionalBinding
import com.cacique.caciqueci.databinding.FragmentCustomersBinding
import com.cacique.caciqueci.ui.BaseScannerFragment

class AdditionalFragment : BaseScannerFragment<FragmentAdditionalBinding>() {

    override val fragmentPrefix: String = "10001"

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAdditionalBinding {
        return FragmentAdditionalBinding.inflate(inflater, container, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.barcodeList
    }
}