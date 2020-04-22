package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.EmpRegDao
import com.example.myapplication.db.dao.LoginDao
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.Login
import com.example.myapplication.helper.CommonMethods

class EmployeeLoginActivity : AppCompatActivity() {
    private val TAG: String= EmployeeLoginActivity::class.java.simpleName
    internal var list=ArrayList<EmpReg>()
    internal var listlogin=ArrayList<Login>()
    lateinit var appDatabase: AppDatabase
    lateinit var empRegisterDao: EmpRegDao
    lateinit var empLoginDao: LoginDao
    lateinit var empid:EditText
    lateinit var password:EditText
    lateinit var submit:TextView
    internal lateinit var commonMethods: CommonMethods
    lateinit var emp_register:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)
        empid=findViewById(R.id.empid)
        password=findViewById(R.id.pins)
        submit=findViewById(R.id.logein)
        emp_register=findViewById(R.id.emp_register)
        appDatabase = AppDatabase.getDatabase(this)
        commonMethods= CommonMethods(this)
        empRegisterDao=appDatabase.empRegDao()
        empLoginDao=appDatabase.loginDao()


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
        else{
            var registerlist=empRegisterDao.getloginlist(Empid,pass)
            Log.e("TAG", " login  " + registerlist.size)
            if (registerlist.size==1){
                listlogin.add(Login(Empid,pass,"Employee"))
                Log.e("TAG", " loginlist  " + listlogin.size)
                Toast.makeText(this,"Login successfully", Toast.LENGTH_SHORT).show()
                empLoginDao.insert(listlogin)
                Log.e(TAG,"insertdata " + empLoginDao.getAll().size)
                intent = Intent(this@EmployeeLoginActivity, EmployeeMainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Login Invalid", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
