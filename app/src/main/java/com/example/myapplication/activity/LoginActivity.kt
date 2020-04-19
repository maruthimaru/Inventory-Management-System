package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

import com.example.myapplication.R
import com.example.myapplication.helper.CommonMethods

class LoginActivity : AppCompatActivity() {
    lateinit var admin_image:ImageView

    internal lateinit var commonMethods: CommonMethods
    lateinit var emp_image:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        admin_image=findViewById(R.id.admin_image)

        emp_image=findViewById(R.id.emp_image)
        commonMethods= CommonMethods(this)

        admin_image.setOnClickListener{
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        emp_image.setOnClickListener{
            val intent = Intent(this, EmployeeLoginActivity::class.java)
            startActivity(intent)
        }
    }



}
