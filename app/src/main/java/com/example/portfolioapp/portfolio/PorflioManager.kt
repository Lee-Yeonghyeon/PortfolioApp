package com.example.portfolioapp.portfolio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PorflioManager(
    context: Context?,
    name:String?,
    factory : SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper (context,name,factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE portfolio (name text, startDate text, EndDate text, sort text , content text, image text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}

