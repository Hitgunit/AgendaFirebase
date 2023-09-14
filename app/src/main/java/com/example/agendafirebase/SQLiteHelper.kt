package com.example.agendafirebase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class SQLiteHelper(context: Context): SQLiteOpenHelper(context, "base.bd", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTable = "CREATE TABLE data (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone TEXT, email TEXT)"
        p0!!.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTable = "DROP TABLE IF EXISTS data"
        p0!!.execSQL(dropTable)
        onCreate(p0)
    }
}