package com.cacique.caciqueci.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cacique.caciqueci.R
import com.cacique.caciqueci.business.data.MyDatabaseHelper
import com.cacique.caciqueci.business.utils.ScannedItem

import java.util.UUID
abstract class BaseScannerFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected lateinit var dbHelper: MyDatabaseHelper
    protected abstract val fragmentPrefix: String
    protected lateinit var adapter: ScannedItemAdapter

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getRecyclerView(): RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        dbHelper = MyDatabaseHelper(requireContext())


        adapter = ScannedItemAdapter(dbHelper.getItemsByPrefix(fragmentPrefix))

        setupRecycler()
        setupBarcodeInput()
        setupAddButton()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleBarcode(barcode: String) {
        if (barcode.isBlank()) return

        val existing = dbHelper.getItemsByPrefix(fragmentPrefix)
        if (existing.any { it.barcode == barcode }) {
            Toast.makeText(requireContext(), "Duplicate barcode", Toast.LENGTH_SHORT).show()
            return
        }

        dbHelper.insertItem(fragmentPrefix, barcode)
        adapter.updateItems(dbHelper.getItemsByPrefix(fragmentPrefix))
        Toast.makeText(requireContext(), "Scanned: $barcode", Toast.LENGTH_SHORT).show()
    }


    private fun setupBarcodeInput() {
        val input = binding.root.findViewById<EditText>(R.id.inputBarcode)

        input.setOnEditorActionListener { _, _, _ ->
            handleBarcode(input.text.toString().trim())
            input.setText("") // clear after processing
            true
        }

        // Always request focus
        input.requestFocus()

        // Optional: re-request focus if lost
        input.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) v.post { v.requestFocus() }
        }
    }


    private fun setupRecycler() {
        val recycler = getRecyclerView()
        recycler.layoutManager = LinearLayoutManager(requireContext()) // 🔥 Este es el que falta
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)
    }


    private fun setupAddButton() {
        binding.root.findViewById<View>(R.id.btnAdd)?.setOnClickListener {
            val input = binding.root.findViewById<EditText>(R.id.inputBarcode)
            val barcode = input.text.toString().trim()
            handleBarcode(barcode)
            input.setText("")
        }
    }

    open fun generateFakeBarcode(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    class ScannedItemAdapter(private var items: List<ScannedItem>) :
        RecyclerView.Adapter<ScannedItemAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val barcodeText: TextView = itemView.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.barcodeText.text = items[position].barcode
        }

        override fun getItemCount(): Int = items.size

        fun updateItems(newItems: List<ScannedItem>) {
            items = newItems
            notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.findViewById<EditText>(R.id.inputBarcode).requestFocus()
    }


}
