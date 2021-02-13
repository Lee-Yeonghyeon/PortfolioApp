package com.example.portfolioapp.certificate

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CertificateManager(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        //certificate라는 테이블 생성(자격증 이름, 취득 날짜, 유효기간, 비고, 관련 url)
        db!!.execSQL("CREATE TABLE certificate (name text, date text, period text, etc text, url text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

}