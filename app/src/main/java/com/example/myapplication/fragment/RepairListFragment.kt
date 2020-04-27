package com.example.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activity.EmployeeLoginActivity
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.EmpRegDao
import com.example.myapplication.db.dao.ProductDetailsDao
import com.example.myapplication.db.table.EmpReg
import com.example.myapplication.db.table.ProductDetails
import com.example.myapplication.helper.CommonMethods
import com.example.myapplication.helper.DialogToast
import com.example.myapplication.utils.QRCodeScannerPortait
import com.example.myapplication.utils.StringsValue
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.IOException
import java.util.ArrayList

class RepairListFragment : Fragment() {
    private val TAG: String= RepairListFragment::class.java.simpleName
    internal var list=ArrayList<ProductDetails>()
    internal lateinit var view: View
    internal lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton
    internal lateinit var dialogToast: DialogToast
    lateinit var appDatabase: AppDatabase
    lateinit var productDao: ProductDetailsDao
    internal lateinit var commonMethods: CommonMethods

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view=inflater.inflate(R.layout.fragment_repair_list,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab = view.findViewById(R.id.fab)
        recyclerView=view.findViewById(R.id.recyclerView)
        appDatabase = AppDatabase.getDatabase(activity!!)
        commonMethods= CommonMethods(activity!!)
        productDao=appDatabase.productDetailDao()

        fab.setOnClickListener{

         val connectIntent = Intent(activity, QRCodeScannerPortait::class.java)
            startActivityForResult(connectIntent, 20)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            val bundle = data!!.extras
            val id = bundle!!.getString(StringsValue.param)
            Log.e("TAG", "onActivityResult: " + id!!)
            if (id == null) {
                dialogToast.toastMethod(activity!!, resources.getString(R.string.cancel))
            } else {
                // consigmentNo.setText(id)
                var productdetails = productDao.getid(id)
                Log.e("TAG", " product_id  " + productdetails.size)
                if (productdetails.size == 0) {
                    dialogToast.toastMethod(activity!!, resources.getString(R.string.InvalidQRcode))
                } else {
                    productdetails[0].pName
                    Log.e("TAG", " product_id  " + productdetails[0].pName)

                    val alertDialog = androidx.appcompat.app.AlertDialog.Builder(activity!!)
                    val inflater = activity!!.layoutInflater
                    val view = inflater.inflate(R.layout.alert_dialog, null)
                    alertDialog.setView(view)
                    val confirmDialog = alertDialog.create()
                    confirmDialog.show()
                    confirmDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    val productid = view.findViewById<TextView>(R.id.product_id)
                    val productname = view.findViewById<TextView>(R.id.product_name)
                    val productcode = view.findViewById<TextView>(R.id.product_code)
                    val productimage = view.findViewById<TextView>(R.id.product_image)
                    val prod_datetime = view.findViewById<TextView>(R.id.prodate_time)
                    val buttonCancel = view.findViewById<Button>(R.id.buttonCancel)
                    val buttonOk = view.findViewById<Button>(R.id.buttonOk)

                    productid.setText( productdetails[0].id)
                    productname.setText( productdetails[0].pName)
                    productcode.setText( productdetails[0].pCode)
                    productimage.setText( productdetails[0].image)
                    prod_datetime.setText( productdetails[0].dateTime)


                    buttonOk.setOnClickListener { v ->
                        //confirmDialog.dismiss()

                    }
                    buttonCancel.setOnClickListener{
                        confirmDialog.dismiss()
                    }



                }
            }
        }

    }




    private fun setfragment(_fragment: Fragment) {
        val fm = fragmentManager
        val fragmentTransaction = fm!!.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, _fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}