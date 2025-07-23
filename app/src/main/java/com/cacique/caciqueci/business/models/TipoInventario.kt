package com.cacique.caciqueci.business.models

import java.io.Serializable

data class TipoInventario(
    var id_tipo_inventario: String,
    var tipo_inventario: String
) : Serializable
