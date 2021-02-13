package com.example.portfolioapp.prize

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PrizeManager(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        //prize라는 테이블 생성(대회이름, 수상이름, 수상날짜, 수상 내역, 수상 비고, 관련 url)
        db!!.execSQL("CREATE TABLE prize (name text, prizename text, date text, contents text, url text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}