package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.EmpRegDao
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.helper.CommonMethods

import kotlinx.android.synthetic.main.activity_employee_login.*

class EmployeeRegsiterActivity : AppCompatActivity() {
    private val TAG: String= EmployeeRegsiterActivity::class.java.simpleName
    internal var list=ArrayList<EmpReg>()
   lateinit var appDatabase: AppDatabase
    lateinit var empRegisterDao: EmpRegDao
    lateinit var empname:EditText
    lateinit var empnumber:EditText
    lateinit var emp_id:EditText
    lateinit var age:EditText
    lateinit var type:EditText
    lateinit var datetime:EditText
    lateinit var password:EditText
    lateinit var submit_btn:Button
    internal lateinit var commonMethods: CommonMethods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_regsiter)
        empname=findViewById(R.id.empname)
        empnumber=findViewById(R.id.empnumber)
        emp_id=findViewById(R.id.emp_id)
        age=findViewById(R.id.age)
        type=findViewById(R.id.type)
        datetime=findViewById(R.id.datetime)
        password=findViewById(R.id.password)
        submit_btn=findViewById(R.id.submit_btn)
        appDatabase = AppDatabase.getDatabase(this)
        commonMethods= CommonMethods(this)
        empRegisterDao=appDatabase.empRegDao()


        submit_btn.setOnClickListener { askAppointment() }
    }
    private fun askAppointment(){
        val Empname=empname.text.toString()
        val Empnum=empnumber.text.toString()
        val Empid=emp_id.text.toString()
        val Age=age.text.toString()
        val Type=type.text.toString()
        val Datetime=datetime.text.toString()
        val Pass=password.text.toString()
        if (Empname.isNullOrEmpty()){
            empname.requestFocus()
            empname.error="Please enter employee name"
        }else if (Empnum.isNullOrEmpty()){
            empnumber.requestFocus()
            empnumber.error="Please enter employee number"
        }else if (Empid.isNullOrEmpty()){
            emp_id.requestFocus()
            emp_id.error="Please enter employee id"
        }else if (Age.isNullOrEmpty()){
            age.requestFocus()
            age.error="Please enter age"
        }else if (Type.isNullOrEmpty()){
            type.requestFocus()
            type.error="Please enter type"
        }else if (Datetime.isNullOrEmpty()){
            datetime.requestFocus()
            datetime.error="please enter date and time"
        }else if (Pass.isNullOrEmpty()){
            password.requestFocus()
            password.error="Please enter password"
        }else{
         list.add(EmpReg(Empname,Age,Empnum,Empid,Pass,Type,Datetime))
            Log.e("TAG", " doctorregister  " + list.size)
            Toast.makeText(this,"Register successfully", Toast.LENGTH_SHORT).show()
            empRegisterDao.insert(list)
            Log.e(TAG,"insertdata " + empRegisterDao.getAll().size)
            intent = Intent(this@EmployeeRegsiterActivity,EmployeeLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
