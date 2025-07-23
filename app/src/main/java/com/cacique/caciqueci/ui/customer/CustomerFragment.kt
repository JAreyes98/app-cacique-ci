package com.cacique.caciqueci.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cacique.caciqueci.databinding.FragmentCustomersBinding
import com.cacique.caciqueci.databinding.FragmentSellpointBinding
import com.cacique.caciqueci.databinding.FragmentSupermarketBinding
import com.cacique.caciqueci.ui.BaseScannerFragment

class CustomerFragment : BaseScannerFragment<FragmentCustomersBinding>() {

    override val fragmentPrefix: String = "20000"

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCustomersBinding {
        return FragmentCustomersBinding.inflate(inflater, container, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.barcodeList
    }
}