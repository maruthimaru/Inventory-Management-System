package com.example.myapplication.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.AdminContactDao
import com.example.myapplication.db.table.AdminContact
import com.example.myapplication.helper.CommonMethods

class AdminContactFragment : Fragment(), View.OnClickListener  {
    private val TAG: String= AdminContactFragment::class.java.simpleName
    internal var adminContact=ArrayList<AdminContact>()
    lateinit var appDatabase: AppDatabase
    lateinit var adminContactDao: AdminContactDao
    internal lateinit var commonMethods: CommonMethods
    internal lateinit var view: View

    override fun onClick(p0: View?) {
        val id = p0!!.id
        when (id) {
            R.id.submit -> {
                val id = adminid!!.text.toString()


                if (id == "") run {
                    adminid!!.requestFocus()
                    adminid!!.error = "please Enter Phone"
                }  else {
                    //getNotification()
                    var list=adminContactDao.getAll()
                    if(list.size==0) {
                        adminContact.add(AdminContact(id))
                        Log.e("TAG", " adminContact  " + adminContact.size)
                        Toast.makeText(activity!!,"Insert successfully", Toast.LENGTH_SHORT).show()
                        adminContactDao.insert(adminContact)
                        Log.e(TAG,"insertdata " + adminContactDao.getAll().size)

                    }else{
                        adminContactDao.update(id,list[0].id)
                        Toast.makeText(activity!!,"Update successfully", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    lateinit var adminid:EditText
    private var submit: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view= inflater.inflate(R.layout.activity_admin_contact,container,false)
        adminid=view.findViewById(R.id.phone)
        submit=view.findViewById(R.id.submit)
        appDatabase = AppDatabase.getDatabase(activity!!)
        commonMethods= CommonMethods(activity!!)
        adminContactDao=appDatabase.adminContactDao()
        var list=adminContactDao.getAll()
        if(list.size>0) {
            adminid.setText(list[0].phone)
        }
        submit!!.setOnClickListener(this)
        return view
    }

}
