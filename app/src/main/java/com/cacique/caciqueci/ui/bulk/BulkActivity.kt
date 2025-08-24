package com.cacique.caciqueci.ui.bulk

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cacique.caciqueci.R
import com.cacique.caciqueci.business.data.MyDatabaseHelper
import com.cacique.caciqueci.business.models.BulkItem
import com.cacique.caciqueci.business.models.BulkRequest
import com.cacique.caciqueci.business.models.InventarioActivo
import com.cacique.caciqueci.business.models.TipoInventario
import com.cacique.caciqueci.business.models.wrapper.DataWrapper
import com.cacique.caciqueci.business.rest.conf.RetrofitFactory
import com.cacique.caciqueci.business.rest.service.InventarioActivoService
import com.cacique.caciqueci.business.rest.service.InventoryService
import com.cacique.caciqueci.business.rest.service.TipoInventarioService
import com.cacique.caciqueci.business.utils.CatalogCallBack
import com.cacique.caciqueci.business.utils.adapter.InventarioActivoAdapter
import com.cacique.caciqueci.business.utils.adapter.TipoInventarioAdapter
import com.cacique.caciqueci.business.utils.cons.ActivityRequestCode
import com.cacique.caciqueci.business.utils.enums.HttpStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BulkActivity : AppCompatActivity(), CatalogCallBack, ActivityRequestCode {

    private val tipoInventarioService = RetrofitFactory.createService(TipoInventarioService::class.java)
    private val inventarioActivoService = RetrofitFactory.createService(InventarioActivoService::class.java)
    private val inventoryService = RetrofitFactory.createService(InventoryService::class.java)

    private var selectedInventarioActivo: InventarioActivo? = null
    private var selectedTipoInventario: TipoInventario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulk)

        val buttonBulk = findViewById<Button>(R.id.btnBulk)
        buttonBulk.setOnClickListener { onClickBulk(it) }

        // Spinner InventarioActivo
        val spinnerInventarioActivo = findViewById<Spinner>(R.id.spinnerInventarioActivo)
        spinnerInventarioActivo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedInventarioActivo = parent.getItemAtPosition(position) as InventarioActivo
                buttonBulk.isEnabled = true
                Toast.makeText(applicationContext, selectedInventarioActivo?.id_Inventario.toString(), Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Spinner TipoInventario
        val spinnerTipo = findViewById<Spinner>(R.id.spinnerTipoInventario)
        spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                Toast.makeText(applicationContext, "Tipo invetario selected ", Toast.LENGTH_LONG)
                selectedTipoInventario = parent.getItemAtPosition(position) as TipoInventario
                Toast.makeText(applicationContext, selectedTipoInventario?.id_tipo_inventario.toString(), Toast.LENGTH_LONG).show()

                inventarioActivoService.obtenerInventarioActivo(selectedTipoInventario!!.id_tipo_inventario)
                    .enqueue(catalogCallbackList(
                        InventarioActivoAdapter::class.java,
                        applicationContext,
                        spinnerInventarioActivo
                    ))
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

//        tipoInventarioService.obtenerTipoInventario().enqueue(catalogCallbackList(
//            TipoInventarioAdapter::class.java,
//            applicationContext,
//            spinnerTipo
//        ))
        val tipoInventarioList = listOf(
            TipoInventario("CI", "Control Interno")
        )
        val tipoAdapter = TipoInventarioAdapter(
            this, // context
            android.R.layout.simple_spinner_item,
            tipoInventarioList
        )
        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipo.adapter = tipoAdapter


    }

    enum class StoreCode(val code: String) {
        SUPERMARKET("10000"),
        CUSTOMER("20000"),
        SELLPOINT("30000"),
        ADDITIONAL("10001")
    }



    fun onClickBulk(view: View) {
        Toast.makeText(applicationContext, "El proceso de volcado está comenzando...", Toast.LENGTH_LONG).show()

        val items = mutableListOf<BulkItem>()
        val dbHelper = MyDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        //Supermarket
        items.add(BulkItem(StoreCode.SUPERMARKET.code))
        val cursor1 = db.rawQuery("SELECT id, barcode, timestamp FROM scanned_items where prefix ='${StoreCode.SUPERMARKET.code}' ORDER BY id DESC", null)
        while (cursor1.moveToNext()) {
            val barcode = cursor1.getString(1)
            items.add(BulkItem(barcode))
        }
        cursor1.close()

        //Customer
        items.add(BulkItem(StoreCode.CUSTOMER.code))
        val cursor2 = db.rawQuery("SELECT id, barcode, timestamp FROM scanned_items where prefix ='${StoreCode.CUSTOMER.code}' ORDER BY id DESC", null)
        while (cursor2.moveToNext()) {
            val barcode = cursor2.getString(1)
            items.add(BulkItem(barcode))
        }
        cursor2.close()

        //Sell point
        items.add(BulkItem(StoreCode.SELLPOINT.code))
        val cursor3 = db.rawQuery("SELECT id, barcode, timestamp FROM scanned_items where prefix ='${StoreCode.SELLPOINT.code}' ORDER BY id DESC", null)
        while (cursor3.moveToNext()) {
            val barcode = cursor3.getString(1)
            items.add(BulkItem(barcode))
        }
        cursor3.close()

        //Additional
        items.add(BulkItem(StoreCode.ADDITIONAL.code))
        val cursor4 = db.rawQuery("SELECT id, barcode, timestamp FROM scanned_items where prefix ='${StoreCode.ADDITIONAL.code}' ORDER BY id DESC", null)
        while (cursor4.moveToNext()) {
            val barcode = cursor4.getString(1)
            items.add(BulkItem(barcode))
        }

        val request = BulkRequest().apply {
            tipoInventario = selectedTipoInventario?.id_tipo_inventario
            inventarioActivo = selectedInventarioActivo?.id_Inventario?.toInt() ?: 0
            setItems(items)
        }
        cursor4.close()

        inventoryService.bulkData(request).enqueue(object : Callback<DataWrapper<String>> {
            override fun onResponse(call: Call<DataWrapper<String>>, response: Response<DataWrapper<String>>) {
                val httpStatus = HttpStatus.resolve(response.code())

                if (response.isSuccessful && httpStatus == HttpStatus.OK) {
                    Toast.makeText(applicationContext, "Volcado ha sido exitoso", Toast.LENGTH_LONG).show()
                    dbHelper.onUpgrade(db, 0, 0)
                    db.close()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "El volcado ha fallado", Toast.LENGTH_LONG).show()
                    println(response.body())
                    db.close()
                }
            }

            override fun onFailure(call: Call<DataWrapper<String>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error al hacer el volcado", Toast.LENGTH_LONG).show()
                db.close()
            }
        })
    }
}
