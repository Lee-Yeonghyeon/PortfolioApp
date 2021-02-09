package com.example.portfolioapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.portfolioapp.HomeActivity.HomeActivity

class MainActivity : AppCompatActivity() {

    lateinit var loginDBManager: LoginDBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var edt_loginId: EditText
    lateinit var edt_loginPassword: EditText
    lateinit var btn_join: Button
    lateinit var btn_login: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide() //액션바 보이지 않음

        edt_loginId = findViewById(R.id.edt_loginId)
        edt_loginPassword = findViewById(R.id.edt_loginPassword)
        btn_join = findViewById(R.id.btn_join)
        btn_login = findViewById(R.id.btn_login)

        loginDBManager = LoginDBManager(this, "loginDB", null, 1)
        sqlitedb = loginDBManager.readableDatabase

        var cursor: Cursor

        //회원가입 버튼을 눌렀을 때 이벤트 - 회원가입 창으로 이동
        btn_join.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        //로그인 버튼을 눌렀을 때 이벤트 - 로그인 진행
        btn_login.setOnClickListener {
            var boolLoginCheck: Boolean = false

            var str_id: String = edt_loginId.text.toString()
            var str_password: String= edt_loginPassword.text.toString()

            if (str_id.length == 0 || str_password.length == 0) {
                Toast.makeText(this, "모든 항목에 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                cursor = sqlitedb.rawQuery("SELECT lId FROM loginDB WHERE lId = '" + str_id +"';", null)
                if (cursor.getCount() != 1) {
                    Toast.makeText(this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    cursor = sqlitedb.rawQuery("SELECT lPassword FROM loginDB WHERE lId = '" + str_id + "';", null)
                    cursor.moveToNext()
                    if(!str_password.equals(cursor.getString(0))) {
                        Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "환영합니다.", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    }
                }

            }

        }
    }

}