package com.example.myapplication.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.Repair_ProductAdapter
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.dao.RepairProductDao
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.db.table.RepairProduct
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.Constants
import com.example.myapplication.utils.QRCodeScannerPortait
import com.example.myapplication.utils.StringsValue
import java.lang.Exception
import java.util.ArrayList

class AdminRepairListFragment:Fragment(),Repair_ProductAdapter.ListAdapterListener{
    private val TAG: String= AdminRepairListFragment::class.java.simpleName
    internal var list= ArrayList<ProductDetails>()
    internal var repairlist= ArrayList<RepairProduct>()
    internal lateinit var recyclerView: RecyclerView
    lateinit var repairadapter: Repair_ProductAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    lateinit var repairporductDao: RepairProductDao
    internal lateinit var commonMethods: CommonMethods

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_admin_repair_list,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.recyclerView)
        appDatabase = AppDatabase.getDatabase(activity!!)
        commonMethods= CommonMethods(activity!!)
        productDao=appDatabase.productDetailDao()
        repairporductDao=appDatabase.repairProductDao()

//        val connectIntent = Intent(activity, QRCodeScannerPortait::class.java)
//        startActivityForResult(connectIntent, 20)

        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = lLayout
        repairlist= repairporductDao.getAll() as ArrayList<RepairProduct>
        Log.e(TAG,"repairlistttt " + repairlist.size )
        repairadapter = Repair_ProductAdapter( activity!!,repairlist,this@AdminRepairListFragment )
        recyclerView.adapter = repairadapter
    }



    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun onClickButton(position: Int, repairProduct: RepairProduct) {


    }

    override fun onClickCheckOut(repairProduct: RepairProduct) {
    }

    override fun onClickButtonInfo(position: Int, repairProduct: RepairProduct) {
        addNewdialog(repairProduct)
    }
    fun addNewdialog(repairProduct: RepairProduct){
        val builder: Dialog = Dialog(activity!!)
        val inflater = layoutInflater
        builder.setTitle("With RatingBar")
        val dialogLayout: View = inflater.inflate(R.layout.fragment_admin_alert_dialog, null)
        builder.setContentView(dialogLayout)
        val message_edittxt = builder.findViewById<EditText>(R.id.message_edittxt)
        val update_txt = builder.findViewById<TextView>(R.id.update_txt)

        update_txt.setOnClickListener {
            val text=message_edittxt.toString()
            if (text.length>0){
                message_edittxt.setError(null)
                repairporductDao.update(text, repairProduct.id)
                Toast.makeText(activity,"Update Successfull",Toast.LENGTH_SHORT).show()
                builder.dismiss()
            }else{

                message_edittxt.requestFocus()
                message_edittxt.error= "Please enter the message"
            }

            Log.e(TAG,"repair " + repairProduct.id )
        }


        builder.show()
    }

}