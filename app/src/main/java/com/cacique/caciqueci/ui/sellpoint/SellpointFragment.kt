package com.cacique.caciqueci.ui.sellpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cacique.caciqueci.databinding.FragmentSellpointBinding
import com.cacique.caciqueci.databinding.FragmentSupermarketBinding
import com.cacique.caciqueci.ui.BaseScannerFragment

class SellpointFragment : BaseScannerFragment<FragmentSellpointBinding>() {

    override val fragmentPrefix: String = "30000"

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSellpointBinding {
        return FragmentSellpointBinding.inflate(inflater, container, false)
    }

    override fun getRecyclerView(): RecyclerView {
        return binding.barcodeList
    }
}
