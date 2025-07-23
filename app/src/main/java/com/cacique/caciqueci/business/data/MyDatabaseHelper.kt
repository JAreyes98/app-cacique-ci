package com.cacique.caciqueci.business.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cacique.caciqueci.business.utils.ScannedItem

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "scanner_app.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """CREATE TABLE scanned_items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                prefix VARCHAR(10) NOT NULL,
                barcode TEXT NOT NULL,
                timestamp TEXT NOT NULL
            )"""
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS scanned_items")
        onCreate(db)
    }

    fun insertItem(prefix: String, barcode: String) {
        val db = writableDatabase
        val stmt = db.compileStatement("INSERT INTO scanned_items (prefix, barcode, timestamp) VALUES (?, ?, datetime('now'))")
        stmt.bindString(1, prefix)
        stmt.bindString(2, barcode)
        stmt.executeInsert()
        stmt.close()
    }

    fun getItemsByPrefix(prefix: String): List<ScannedItem> {
        val items = mutableListOf<ScannedItem>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT id, prefix, barcode, timestamp FROM scanned_items WHERE prefix = ? ORDER BY id DESC",
            arrayOf(prefix)
        )
        cursor.use {
            while (it.moveToNext()) {
                items.add(
                    ScannedItem(
                        id = it.getLong(0),
                        prefix = it.getString(1),
                        barcode = it.getString(2),
                        timestamp = it.getString(3)
                    )
                )
            }
        }
        return items
    }

}
