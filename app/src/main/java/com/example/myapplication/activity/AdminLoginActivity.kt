package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.table.Login
import com.example.myapplication.helper.CommonMethods

class AdminLoginActivity : AppCompatActivity(), View.OnClickListener  {
    private val TAG: String= AdminLoginActivity::class.java.simpleName
    internal var listlogin=ArrayList<Login>()
    lateinit var appDatabase: AppDatabase
    lateinit var empLoginDao: LoginDao
    internal lateinit var commonMethods: CommonMethods
    override fun onClick(p0: View?) {
        val id = p0!!.id
        when (id) {
            R.id.submit -> {
                val id = adminid!!.text.toString()


                if (id == "") run {
                    adminid!!.requestFocus()
                    adminid!!.error = "please Enter Admin id"
                }  else {
                    //getNotification()
                    if(id=="1234") {
                        listlogin.add(Login(id,"","Admin"))
                        Log.e("TAG", " loginlist  " + listlogin.size)
                        Toast.makeText(this,"Login successfully", Toast.LENGTH_SHORT).show()
                        empLoginDao.insert(listlogin)
                        Log.e(TAG,"insertdata " + empLoginDao.getAll().size)
                        intent = Intent(this@AdminLoginActivity, AdminMainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        adminid!!.error = "Invalid id"
                    }
                }
            }
        }
    }
    lateinit var adminid:EditText
    private var submit: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
        adminid=findViewById(R.id.admin_id)
        submit=findViewById(R.id.submit)
        appDatabase = AppDatabase.getDatabase(this)
        commonMethods= CommonMethods(this)
        empLoginDao=appDatabase.loginDao()

        submit!!.setOnClickListener(this)

    }

}
