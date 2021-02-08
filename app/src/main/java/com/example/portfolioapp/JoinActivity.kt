package com.example.portfolioapp

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class JoinActivity : AppCompatActivity() {

    lateinit var loginDBManager: LoginDBManager
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var edt_joinName: EditText
    lateinit var edt_joinId: EditText
    lateinit var btn_joinIdCheck: Button
    lateinit var edt_joinPassword1: EditText
    lateinit var edt_joinPassword2: EditText
    lateinit var btn_joinBack: Button
    lateinit var btn_join: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        supportActionBar?.hide()

        edt_joinName = findViewById(R.id.edt_joinName)
        edt_joinId = findViewById(R.id.edt_joinId)
        btn_joinIdCheck = findViewById(R.id.btn_joinIdCheck)
        edt_joinPassword1 = findViewById(R.id.edt_joinPassword1)
        edt_joinPassword2 = findViewById(R.id.edt_joinPassword2)
        btn_joinBack = findViewById(R.id.btn_joinBack)
        btn_join = findViewById(R.id.btn_join)

        var boolIdCheck: Boolean = false

        loginDBManager = LoginDBManager(this, "loginDB", null, 1)

        //아이디 중복 확인 버튼을 눌렀을 때 이벤트 - DB에서 아이디 중복 여부를 확인함
        btn_joinIdCheck.setOnClickListener {
            sqlitedb = loginDBManager.readableDatabase

            var cursor: Cursor
            cursor = sqlitedb.rawQuery("SELECT lId FROM loginDB WHERE lId = '" + edt_joinId.text.toString() + "';", null)

            if(cursor.getCount() != 0) {
                boolIdCheck = false
                Toast.makeText(this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
            else{
                boolIdCheck = true
                Toast.makeText(this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        //취소 버튼을 눌렀을 때 이벤트 - 로그인 창으로 이동
        btn_joinBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //회원가입 버튼을 눌렀을 때 이벤트 - 빈 칸이 없는지, 아이디 중복이 아닌지, 비밀번호를 일치되게 적었는지 확인 후 회원가입 진행
        btn_join.setOnClickListener {
            var str_name: String = edt_joinName.text.toString()
            var str_id: String = edt_joinId.text.toString()
            var str_password1: String = edt_joinPassword1.text.toString()
            var str_password2: String = edt_joinPassword2.text.toString()

            sqlitedb = loginDBManager.writableDatabase

            if (str_name.length == 0 || str_id.length == 0 || str_password1.length ==0 || str_password2.length == 0){
            Toast.makeText(this, "모든 항목에 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (boolIdCheck != true) {
                Toast.makeText(this, "아이디 중복 여부를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (str_password1 != str_password2) {
                Toast.makeText(this, "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                sqlitedb.execSQL("INSERT INTO loginDB VALUES('$str_name', '$str_id', '$str_password1');")
                sqlitedb.close()
                Toast.makeText(this, "$str_name 님 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

