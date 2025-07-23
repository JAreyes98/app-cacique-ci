package com.cacique.caciqueci.ui.supermarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cacique.caciqueci.databinding.FragmentSupermarketBinding
import com.cacique.caciqueci.ui.BaseScannerFragment
import com.cacique.caciqueci.ui.additional.SupermarketViewModel

class SupermarketFragment : BaseScannerFragment<FragmentSupermarketBinding>() {

    override val fragmentPrefix: String = "10000"

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSupermarketBinding {
        return FragmentSupermarketBinding.inflate(inflater, container, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.barcodeList
    }
}

