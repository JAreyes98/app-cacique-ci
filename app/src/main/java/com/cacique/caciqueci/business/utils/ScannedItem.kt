package com.cacique.caciqueci.business.utils

data class ScannedItem(
    val id: Long,
    val prefix: String,
    val barcode: String,
    val timestamp: String
)
