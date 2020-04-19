package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.R

class EmployeeLoginActivity : AppCompatActivity() {
    lateinit var empid:EditText
    lateinit var password:EditText
    lateinit var submit:TextView
    lateinit var emp_register:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)
        empid=findViewById(R.id.empid)
        password=findViewById(R.id.pins)
        submit=findViewById(R.id.logein)
        emp_register=findViewById(R.id.emp_register)

        submit.setOnClickListener {
            askAppointment()
        }
        emp_register.setOnClickListener {
            newReg()
        }
    }
    private fun newReg(){
        intent = Intent(this@EmployeeLoginActivity,EmployeeRegsiterActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun askAppointment(){
        val Empid=empid.text.toString()
        val pass=password.text.toString()
        if (Empid.isNullOrEmpty()){
            empid.requestFocus()
            empid.error="Please enter employee id"
        }else if (pass.isNullOrEmpty()){
            password.requestFocus()
            password.error="Please enter password"
        }
    }
}
